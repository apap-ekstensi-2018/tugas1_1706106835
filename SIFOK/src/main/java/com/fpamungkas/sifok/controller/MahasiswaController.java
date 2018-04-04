package com.fpamungkas.sifok.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpamungkas.sifok.service.StudentService;
import com.fpamungkas.sifok.model.StudentModel;
import com.fpamungkas.sifok.model.ProgramStudiModel;
import com.fpamungkas.sifok.model.UniversitasModel;
import com.fpamungkas.sifok.model.FakultasModel;

import org.springframework.validation.BindingResult;

@Controller
public class MahasiswaController
{
    @Autowired
    StudentService studentDAO;
	
	@RequestMapping("/")
	public String index()
	{
		return "mahasiswa";
	}
	
	@RequestMapping(value = "/mahasiswa", method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "npm", required = false) String npm, Model model)
	{
		 StudentModel student = studentDAO.selectStudent(npm);

	        if (student != null) {
	            model.addAttribute ("student", student);
	            return "viewmahasiswa";
	        } else {
	            model.addAttribute ("npm", npm);
	            return "not-found";
	        }
	}
	
	@GetMapping("/mahasiswa/tambah")
	public String initialTambah(Model model)
	{
		List<ProgramStudiModel> prodi = studentDAO.selectProdi();
		model.addAttribute("prodi",prodi);
		return "tambahdata";
	}
	
	@RequestMapping("/mahasiswa/cari")
	public String cariFakultas(
			@RequestParam(value = "univ", required = false) String id_universitas,
			@RequestParam(value = "fakultas", required = false) String id_fakultas,
			@RequestParam(value = "prodi", required = false) String id_prodi,
			Model model)
	{
		if(id_universitas == null) {
			List<UniversitasModel> univ = studentDAO.selectUniversitas();
			model.addAttribute("universitas",univ);
			return "carimahasiswa";
		}else if(id_fakultas == null){
			List<FakultasModel> fakultas = studentDAO.selectFakultas(id_universitas);
			model.addAttribute("id_univ", id_universitas);
			List<UniversitasModel> univ = studentDAO.selectUniversitas();
			model.addAttribute("universitas",univ);
			model.addAttribute("fakultas", fakultas);
			return "carimahasiswa";
		}else if(id_prodi == null){
			List<FakultasModel> fakultas = studentDAO.selectFakultas(id_universitas);
			model.addAttribute("id_univ", id_universitas);
			List<UniversitasModel> univ = studentDAO.selectUniversitas();
			List<ProgramStudiModel> prodi = studentDAO.selectProdiByFakultas(id_fakultas);
			model.addAttribute("universitas",univ);
			model.addAttribute("fakultas", fakultas);
			model.addAttribute("id_fakultas",id_fakultas);
			model.addAttribute("prodi", prodi);
			return "carimahasiswa";
		}else {
			List<StudentModel> student = studentDAO.selectListStudent(id_prodi);
			model.addAttribute("students",student);
			if(!student.isEmpty()) {
				model.addAttribute("prodi",student.get(0).getProgram_studi());
				model.addAttribute("fakultas",student.get(0).getFakultas());
				model.addAttribute("universitas",student.get(0).getUniversitas());
			}
			return "lihatdaftar";
		}
	}
	
//	@RequestMapping(value = "/mahasiswa/cari", method = RequestMethod.POST)
//	public String cariFakultas(
//			@RequestParam(value = "univ", required = false) String id_universitas,
//			Model model)
//	{
//		List<FakultasModel> fakultas = studentDAO.selectFakultas(id_universitas);
//		model.addAttribute("id_univ", id_universitas);
//		List<UniversitasModel> univ = studentDAO.selectUniversitas();
//		model.addAttribute("universitas",univ);
//		model.addAttribute("fakultas", fakultas);
//		return "carimahasiswa";
//	}
	
//	@RequestMapping("/kelulusan")
//	public String initialKelulusan(Model model)
//	{
//		List<ProgramStudiModel> prodi = studentDAO.selectProdi();
//		model.addAttribute("prodi",prodi);
//		return "kelulusan";
//	}
	
	@RequestMapping(value= "/kelulusan", method = RequestMethod.GET)
	public String kelulusan(
			@RequestParam(value = "tahun", required = false) String tahun,
			@RequestParam(value = "prodi", required = false) String prodi,
			Model model)
	{
		if(prodi!=null || tahun!=null){
		int jumlahMahasiswa = studentDAO.jumlahMahasiswa(prodi, tahun);
		int jumlahKelulusan = studentDAO.jumlahKelulusan(prodi, tahun);
		double persentase = (double)jumlahKelulusan/(double)jumlahMahasiswa;
		
		System.out.println("hade "+ jumlahKelulusan);
		System.out.println("hade 2 "+ jumlahMahasiswa);
		ProgramStudiModel programStudi = studentDAO.selectRincianJurusan(prodi);
		
		model.addAttribute("jumlahMahasiswa", jumlahMahasiswa);
		model.addAttribute("jumlahKelulusan", jumlahKelulusan);
		model.addAttribute("persentase", String.format("%.2f", persentase*100));
		model.addAttribute("programStudi",programStudi);
		model.addAttribute("tahun_masuk",tahun);
		
		return "tampilkelulusan";
		}else {
			List<ProgramStudiModel> prodis = studentDAO.selectProdi();
			model.addAttribute("prodi",prodis);
			return "kelulusan";
		}
		
	}
	
	@RequestMapping(value = "/mahasiswa/tambah", method = RequestMethod.POST)
	public String tambah(
			@Valid @ModelAttribute("student") StudentModel student,
    			BindingResult result,
    			Model model) throws ParseException
	{
		
		String date = student.getTanggal_lahir();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dateStr = formatter.parse(date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
		
		student.setTanggal_lahir(dateDB.toString());
		int urutan = studentDAO.selectUrutan(student.getProgram_studi(), student.getTahun_masuk(), student.getJalur_masuk().substring(0, student.getJalur_masuk().length()-3));
		String kodeProdi = studentDAO.selectKodeProdi(student.getProgram_studi());
		String npm = student.getTahun_masuk().substring(2,4)+studentDAO.selectKodeUniv(student.getProgram_studi())+kodeProdi+student.getJalur_masuk().substring(student.getJalur_masuk().length()-2, student.getJalur_masuk().length())+String.format("%03d",++urutan);
		student.setNpm(npm);
		System.out.println("fff "+student.getJalur_masuk());
		student.setJalur_masuk(student.getJalur_masuk().substring(0, student.getJalur_masuk().length()-3));
		
		if(result.hasErrors())
			return "not-valid-input";
		else if(student.getNpm() == null)
			return "tambahdata";
		else
			studentDAO.addStudent(student);
		
		model.addAttribute("npm", student.getNpm());
		return "success-add";
	}
	
	@RequestMapping("/mahasiswa/ubah/{npm}")
	public String update(
			@PathVariable(value = "npm") String npm, Model model)
	{
		StudentModel student = studentDAO.selectStudent(npm);
		System.out.println("uala "+student.getProgram_studi());
		if(student.getNpm() == null) {
			model.addAttribute("npm",npm);
			return "not-found";
		}
		
		List<ProgramStudiModel> prodi = studentDAO.selectProdi();
		model.addAttribute("prodi",prodi);
		model.addAttribute("student",student);
		return "form-update";
	}
	
	@RequestMapping(value="/mahasiswa/ubah/{npm}", method = RequestMethod.POST)
	public String submitUpdate(
			@Valid @ModelAttribute("student") StudentModel student,
			BindingResult result,
			Model model) throws ParseException
	{
		String date = student.getTanggal_lahir();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dateStr = formatter.parse(date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
		
		student.setTanggal_lahir(dateDB.toString());
		int urutan = studentDAO.selectUrutan(student.getProgram_studi(), student.getTahun_masuk(), student.getJalur_masuk());
		System.out.println("sayala "+student.getJalur_masuk().substring(student.getJalur_masuk().length()-2, student.getJalur_masuk().length()));
		String npm = student.getTahun_masuk().substring(2,4)+studentDAO.selectKodeUniv(student.getProgram_studi())+student.getProgram_studi()+student.getJalur_masuk().substring(student.getJalur_masuk().length()-2, student.getJalur_masuk().length())+String.format("%03d",urutan);
		student.setNpm(npm);
		student.setJalur_masuk(student.getJalur_masuk().substring(0, student.getJalur_masuk().length()-3));
		model.addAttribute("npm",npm);
		if(result.hasErrors())
			return "not-valid-input";
		else if(student.getNpm() == null)
			return "tambahdata";
		else
			studentDAO.updateStudent(student);
		
		model.addAttribute("npm", student.getNpm());
		return "success-update";
	}
	
}