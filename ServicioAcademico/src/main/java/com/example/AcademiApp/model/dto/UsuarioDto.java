package com.example.AcademiApp.model.dto;

public record UsuarioDto(
   int usu_id,
   String usu_email,
   String usu_pass,
   int usu_numrun, 
   char usu_dvrun,
   String usu_dir,
   String usu_nombre,
   String usu_snombre,
   String usu_appaterno,
   String usu_apmaterno
){}
