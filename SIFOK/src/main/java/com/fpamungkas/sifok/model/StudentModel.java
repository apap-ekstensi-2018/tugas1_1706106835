package com.fpamungkas.sifok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel
{
    private String npm;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private String program_studi;
    private String fakultas;
    private String universitas;
    private int jenis_kelamin;
    private String agama;
    private String golongan_darah;
    private String tahun_masuk;
    private String jalur_masuk;
    private String status;
    private int id_prodi;
    private int id;
    private String kode_prodi;
}
