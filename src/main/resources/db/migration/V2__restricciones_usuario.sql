ALTER TABLE usuario
    ADD CONSTRAINT chk_usuario_rol
        CHECK (rol IN ('ADMIN', 'SUPERVISOR', 'TECNICO')),

    ADD CONSTRAINT chk_usuario_estado
        CHECK (estado IN ('ACTIVO', 'INACTIVO'));
