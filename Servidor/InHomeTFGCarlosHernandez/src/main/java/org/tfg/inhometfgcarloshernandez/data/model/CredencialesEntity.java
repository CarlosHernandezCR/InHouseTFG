package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.tfg.inhometfgcarloshernandez.common.constantes.CredencialesConstantes;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = CredencialesConstantes.TABLE_NAME)
public class CredencialesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CredencialesConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = CredencialesConstantes.COLUMN_ID_USUARIO, nullable = false)
    private UsuarioEntity idUsuario;

    @Size(max = 255)
    @NotNull
    @Column(name = CredencialesConstantes.COLUMN_PASSWORD, nullable = false)
    private String password;

    @NotNull
    @ColumnDefault("0")
    @Column(name = CredencialesConstantes.COLUMN_ACTIVADO, nullable = false)
    private Boolean activado = false;

    @Size(max = 255)
    @Column(name = CredencialesConstantes.COLUMN_CODIGO_ACTIVACION)
    private String codigoActivacion;

    @Column(name = CredencialesConstantes.COLUMN_FECHA_EXPIRACION_CODIGO)
    private Instant fechaExpiracionCodigo;
}