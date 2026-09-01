-- =========================================================
-- ScannerCP
-- Migración V1 - Estructura inicial
-- =========================================================


-- =========================================================
-- USUARIO
-- =========================================================

CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,

    codigo VARCHAR(30) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    estado VARCHAR(30) NOT NULL,

    fecha_registro DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT uk_usuario_codigo UNIQUE (codigo),
    CONSTRAINT uk_usuario_correo UNIQUE (correo)
);


-- =========================================================
-- MAQUINA
-- =========================================================

CREATE TABLE maquina (
    id_maquina BIGINT AUTO_INCREMENT PRIMARY KEY,

    codigo VARCHAR(30) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    area VARCHAR(100),
    ubicacion VARCHAR(150),
    fabricante VARCHAR(100),
    modelo VARCHAR(100),
    numero_serie VARCHAR(100),
    estado VARCHAR(30) NOT NULL,

    fecha_registro DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT uk_maquina_codigo UNIQUE (codigo),
    CONSTRAINT uk_maquina_numero_serie UNIQUE (numero_serie)
);


-- =========================================================
-- PIEZA
-- =========================================================

CREATE TABLE pieza (
    id_pieza BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_maquina BIGINT NOT NULL,

    codigo VARCHAR(30) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    ubicacion VARCHAR(150),
    fabricante VARCHAR(100),
    referencia VARCHAR(100),
    estado VARCHAR(30) NOT NULL,
    codigo_qr VARCHAR(255) NOT NULL,

    fecha_registro DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT uk_pieza_codigo UNIQUE (codigo),
    CONSTRAINT uk_pieza_codigo_qr UNIQUE (codigo_qr),

    CONSTRAINT fk_pieza_maquina
        FOREIGN KEY (id_maquina)
        REFERENCES maquina(id_maquina)
);


-- =========================================================
-- INSPECCION
-- =========================================================

CREATE TABLE inspeccion (
    id_inspeccion BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_pieza BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,

    observacion TEXT,

    requiere_mantenimiento BOOLEAN NOT NULL,

    prioridad VARCHAR(20),

    estado_inicial VARCHAR(30) NOT NULL,

    fecha_hora DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT fk_inspeccion_pieza
        FOREIGN KEY (id_pieza)
        REFERENCES pieza(id_pieza),

    CONSTRAINT fk_inspeccion_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario),

    CONSTRAINT chk_inspeccion_prioridad
        CHECK (
            prioridad IS NULL
            OR prioridad IN ('BAJA', 'ALTA', 'URGENTE')
        ),

    CONSTRAINT chk_inspeccion_estado
        CHECK (
            estado_inicial IN ('EN_PROCESO', 'COMPLETADA')
        ),

    CONSTRAINT chk_inspeccion_mantenimiento
        CHECK (
            (requiere_mantenimiento = FALSE AND prioridad IS NULL)
            OR
            (requiere_mantenimiento = TRUE AND prioridad IS NOT NULL)
        )
);


-- =========================================================
-- ORDEN DE MANTENIMIENTO
-- =========================================================

CREATE TABLE orden_mantenimiento (
    id_orden BIGINT AUTO_INCREMENT PRIMARY KEY,

    codigo VARCHAR(30) NOT NULL,

    id_inspeccion BIGINT NOT NULL,
    id_pieza BIGINT NOT NULL,
    id_usuario_solicitante BIGINT NOT NULL,

    estado VARCHAR(30) NOT NULL,
    prioridad VARCHAR(20) NOT NULL,

    fecha_creacion DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT uk_orden_codigo UNIQUE (codigo),
    CONSTRAINT uk_orden_inspeccion UNIQUE (id_inspeccion),

    CONSTRAINT fk_orden_inspeccion
        FOREIGN KEY (id_inspeccion)
        REFERENCES inspeccion(id_inspeccion),

    CONSTRAINT fk_orden_pieza
        FOREIGN KEY (id_pieza)
        REFERENCES pieza(id_pieza),

    CONSTRAINT fk_orden_usuario
        FOREIGN KEY (id_usuario_solicitante)
        REFERENCES usuario(id_usuario),

    CONSTRAINT chk_orden_prioridad
        CHECK (
            prioridad IN ('BAJA', 'ALTA', 'URGENTE')
        )
);


-- =========================================================
-- MANTENIMIENTO
-- =========================================================

CREATE TABLE mantenimiento (
    id_mantenimiento BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_orden BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,

    descripcion_trabajo TEXT NOT NULL,

    fecha_inicio DATETIME(6) NOT NULL,
    fecha_fin DATETIME(6),

    estado VARCHAR(30) NOT NULL,

    CONSTRAINT fk_mantenimiento_orden
        FOREIGN KEY (id_orden)
        REFERENCES orden_mantenimiento(id_orden),

    CONSTRAINT fk_mantenimiento_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);


-- =========================================================
-- EVIDENCIA DE INSPECCION
-- =========================================================

CREATE TABLE evidencia_inspeccion (
    id_evidencia_inspeccion BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_inspeccion BIGINT NOT NULL,

    archivo VARCHAR(500) NOT NULL,
    tipo VARCHAR(50),

    fecha_registro DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT fk_evidencia_inspeccion
        FOREIGN KEY (id_inspeccion)
        REFERENCES inspeccion(id_inspeccion)
);


-- =========================================================
-- EVIDENCIA DE MANTENIMIENTO
-- =========================================================

CREATE TABLE evidencia_mantenimiento (
    id_evidencia_mantenimiento BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_mantenimiento BIGINT NOT NULL,

    archivo VARCHAR(500) NOT NULL,
    tipo VARCHAR(50),

    fecha_registro DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT fk_evidencia_mantenimiento
        FOREIGN KEY (id_mantenimiento)
        REFERENCES mantenimiento(id_mantenimiento)
);
