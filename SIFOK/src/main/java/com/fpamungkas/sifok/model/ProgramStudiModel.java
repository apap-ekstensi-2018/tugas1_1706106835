package com.fpamungkas.sifok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramStudiModel
{
	private String id;
    private String kode_prodi;
    private String nama_prodi;
    private int id_fakultas;
    private String universitas;
    private String fakultas;
}
