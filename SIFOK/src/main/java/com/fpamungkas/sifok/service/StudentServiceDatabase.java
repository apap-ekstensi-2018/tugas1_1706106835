package com.fpamungkas.sifok.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpamungkas.sifok.dao.StudentMapper;
import com.fpamungkas.sifok.model.StudentModel;
import com.fpamungkas.sifok.model.ProgramStudiModel;
import com.fpamungkas.sifok.model.UniversitasModel;
import com.fpamungkas.sifok.model.FakultasModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
	@Autowired
    private StudentMapper studentMapper;

    public StudentServiceDatabase(){}
    
    public StudentServiceDatabase(StudentMapper studentMapper){
    		this.studentMapper = studentMapper;
    }
    
    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent(npm);
    }
    
    @Override
    public List<ProgramStudiModel> selectProdi()
    {
    		return studentMapper.selectProdi();
    }
    
    @Override
    public boolean addStudent(StudentModel student)
    {
        return studentMapper.addStudent (student);
    }
    
    @Override
    public String selectKodeUniv(String program_studi) {
    		return studentMapper.selectKodeUniv(program_studi);
    }
    
    @Override
    public int selectUrutan(String program_studi, String tahun_masuk, String jalur_masuk) {
    		return studentMapper.selectUrutan(program_studi, tahun_masuk, jalur_masuk);
    }
    
    @Override
    public boolean updateStudent(StudentModel student)
    {
    		return studentMapper.updateStudent(student);
    }
    
    @Override
    public int jumlahKelulusan(String program_studi, String tahun_masuk)
    {
    		return studentMapper.jumlahKelulusan(program_studi, tahun_masuk);
    }
    
    @Override
    public int jumlahMahasiswa(String program_studi, String tahun_masuk)
    {
    		return studentMapper.jumlahMahasiswa(program_studi, tahun_masuk);
    }
    
    @Override
    public int jumlahDO(String program_studi, String tahun_masuk)
    {
    		return studentMapper.jumlahDO(program_studi, tahun_masuk);
    }
    
    @Override
    public ProgramStudiModel selectRincianJurusan(String program_studi)
    {
    		return studentMapper.selectRincianJurusan(program_studi);
    }
    
    @Override
    public List<UniversitasModel> selectUniversitas()
    {
    		return studentMapper.selectUniversitas();
    }
    
    @Override
    public List<FakultasModel> selectFakultas(String id_universitas)
    {
    		return studentMapper.selectFakultas(id_universitas);
    }
    
    @Override
    public List<ProgramStudiModel> selectProdiByFakultas(String id_fakultas)
    {
    		return studentMapper.selectProdibyFakultas(id_fakultas);
    }
    
    @Override
    public List<StudentModel> selectListStudent(String id_prodi)
    {
    		return studentMapper.selectListStudent(id_prodi);
    }
    
    @Override
    public String selectKodeProdi(String id_prodi)
    {
    		return studentMapper.selectKodeProdi(id_prodi);
    }
}