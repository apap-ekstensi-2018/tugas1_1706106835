package com.fpamungkas.sifok.service;

import java.util.List;

import com.fpamungkas.sifok.model.StudentModel;
import com.fpamungkas.sifok.model.ProgramStudiModel;
import com.fpamungkas.sifok.model.UniversitasModel;
import com.fpamungkas.sifok.model.FakultasModel;

public interface StudentService
{
    StudentModel selectStudent (String npm);
    
    List<ProgramStudiModel> selectProdi();
    
    boolean addStudent(StudentModel student);
    
    boolean updateStudent(StudentModel student);
    
    String selectKodeUniv(String program_studi);

    int jumlahMahasiswa(String program_studi, String tahun_masuk);
    
    int jumlahKelulusan(String program_studi, String tahun_masuk);
    
    int jumlahDO(String program_studi, String tahun_masuk);
    
    int selectUrutan(String program_studi, String tahun_masuk, String jalur_masuk);
    
    ProgramStudiModel selectRincianJurusan(String program_studi);
    
    List<UniversitasModel> selectUniversitas();
    
    List<FakultasModel> selectFakultas(String id_universitas);
    
    List<ProgramStudiModel> selectProdiByFakultas(String id_fakultas);
    
    List<StudentModel> selectListStudent(String id_prodi);
    
    String selectKodeProdi(String id_prodi);
//
//    List<StudentModel> selectAllStudents(int start, int leng);
//
//
//    boolean addStudent (StudentModel student);
//
//    boolean deleteStudent (String npm);
//
//    boolean updateStudent (StudentModel student);
}
