package com.fpamungkas.sifok.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fpamungkas.sifok.model.StudentModel;
import com.fpamungkas.sifok.model.ProgramStudiModel;
import com.fpamungkas.sifok.model.UniversitasModel;
import com.fpamungkas.sifok.model.FakultasModel;

@Mapper
public interface StudentMapper
{
    @Select("select mahasiswa.id,npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, kode_prodi, golongan_darah, status, tahun_masuk, jalur_masuk, program_studi.nama_prodi as program_studi, nama_fakultas as fakultas, nama_univ as universitas, id_prodi from mahasiswa, program_studi, fakultas, universitas where npm = #{npm} AND mahasiswa.id_prodi = program_studi.id AND fakultas.id = program_studi.id_fakultas AND fakultas.id_univ = universitas.id")
    StudentModel selectStudent (@Param("npm") String npm);
    
    @Select("select * from program_studi")
    List<ProgramStudiModel> selectProdi();
    
    @Select("SELECT kode_prodi from program_studi where id = #{id_prodi}")
    String selectKodeProdi(String id_prodi);
    
    @Select("select * from universitas")
    List<UniversitasModel> selectUniversitas();
    
    @Select("select distinct * from fakultas where id_univ = #{id_universitas}")
    List<FakultasModel> selectFakultas(@Param("id_universitas") String id_universitas);
    
    @Select("select distinct * from program_studi where program_studi.id_fakultas = #{id_fakultas}")
    List<ProgramStudiModel> selectProdibyFakultas(@Param("id_fakultas") String id_fakultas);
    
    @Select("select kode_univ from universitas,fakultas, program_studi where program_studi.id = #{program_studi} AND fakultas.id = program_studi.id_fakultas AND universitas.id= fakultas.id_univ")
    String selectKodeUniv(@Param("program_studi") String program_studi);
    
    @Select("select nama_prodi, nama_fakultas as fakultas, nama_univ as universitas from program_studi, fakultas, universitas where fakultas.id = program_studi.id_fakultas AND fakultas.id_univ = universitas.id AND program_studi.id = #{program_studi}")
    ProgramStudiModel selectRincianJurusan(@Param("program_studi") String program_studi);
    
    @Select("select count(id) from mahasiswa where id_prodi =  #{program_studi} AND tahun_masuk =  #{tahun_masuk} AND jalur_masuk = #{jalur_masuk}")
    int selectUrutan(@Param("program_studi") String program_studi, @Param("tahun_masuk") String tahun_masuk, @Param("jalur_masuk") String jalur_masuk);
    
    @Select("select mahasiswa.id,npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, program_studi.nama_prodi as program_studi, nama_fakultas as fakultas, nama_univ as universitas, id_prodi from mahasiswa, program_studi, fakultas, universitas where mahasiswa.id_prodi = #{id_prodi} AND mahasiswa.id_prodi = program_studi.id AND fakultas.id = program_studi.id_fakultas AND fakultas.id_univ = universitas.id")
    List<StudentModel> selectListStudent(@Param("id_prodi") String id_prodi);
    
    
    @Update("UPDATE mahasiswa set npm = #{student.npm}, nama = #{student.nama}, tempat_lahir = #{student.tempat_lahir},"
    		+ " jenis_kelamin = #{student.jenis_kelamin}, agama = #{student.agama}, golongan_darah = #{student.golongan_darah},"
    		+ "tahun_masuk = #{student.tahun_masuk}, jalur_masuk = #{student.jalur_masuk}, id_prodi = #{student.program_studi} where id = #{student.id}")
    boolean updateStudent(@Param("student") StudentModel student);
    
    @Select("select count(id) from mahasiswa where id_prodi = #{program_studi} AND tahun_masuk = #{tahun_masuk}")
    int jumlahMahasiswa(@Param("program_studi") String program_studi,@Param("tahun_masuk") String tahun_masuk);
    
    @Select("select count(id) from mahasiswa where id_prodi = #{program_studi} AND tahun_masuk = #{tahun_masuk} AND status = 'Lulus'")
    int jumlahKelulusan(@Param("program_studi") String program_studi, @Param("tahun_masuk") String tahun_masuk);
    
    @Select("select count(id) from mahasiswa where id_prodi = #{program_studi} AND tahun_masuk = #{tahun_masuk} AND status = 'Drop Out'")
    int jumlahDO(@Param("program_studi") String program_studi, @Param("tahun_masuk") String tahun_masuk);
    
    @Insert("INSERT INTO mahasiswa "
    		+ "(npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, tahun_masuk, jalur_masuk, id_prodi)"
    		+ "VALUES (#{student.npm}, #{student.nama}, #{student.tempat_lahir}, #{student.tanggal_lahir},#{student.jenis_kelamin}, #{student.agama}, #{student.golongan_darah}, #{student.tahun_masuk}, #{student.jalur_masuk}, #{student.program_studi})")
    boolean addStudent (@Param("student") StudentModel student);
    
//    
//    @Delete("DELETE FROM student where npm = #{npm}")
//    boolean deleteStudent (String npm);
//    
//    @Update("UPDATE student set name = #{student.name}, gpa = #{student.gpa} where npm = #{student.npm}")
//    boolean updateStudent (@Param("student") StudentModel student);
}
