-- =========================================================
-- ScannerCP
-- Datos de prueba: máquinas y piezas
-- =========================================================


-- =========================================================
-- MÁQUINAS
-- =========================================================

INSERT INTO maquina
(
    codigo,
    nombre,
    descripcion,
    area,
    ubicacion,
    fabricante,
    modelo,
    numero_serie,
    estado
)
VALUES

(
    'MAQ-BOM-001',
    'Bomba centrífuga principal',
    'Bomba utilizada para circulación de líquidos en la línea principal.',
    'LIQUIDOS',
    'Línea de producción 1',
    'Grundfos',
    'CR-15',
    'GRU-BOM-001',
    'OPERATIVA'
),

(
    'MAQ-COMP-001',
    'Compresor de aire',
    'Compresor encargado de alimentar la red neumática de producción.',
    'CUARTO DE MAQUINAS',
    'Sala de compresores',
    'Atlas Copco',
    'GA-18',
    'ATL-COMP-001',
    'OPERATIVA'
),

(
    'MAQ-ENV-001',
    'Envasadora automática',
    'Equipo utilizado para el proceso automático de llenado y envasado.',
    'PRODUCCION',
    'Línea de producción 2',
    'Krones',
    'FILL-2000',
    'KRO-ENV-001',
    'OPERATIVA'
),

(
    'MAQ-ETQ-001',
    'Etiquetadora automática',
    'Máquina encargada de colocar etiquetas sobre el producto terminado.',
    'EMPAQUE',
    'Línea de empaque',
    'Zebra Technologies',
    'ZT-Series',
    'ZEB-ETQ-001',
    'MANTENIMIENTO'
),

(
    'MAQ-MEZ-001',
    'Tanque mezclador',
    'Tanque industrial con motor y agitador para preparación de producto.',
    'LIQUIDOS',
    'Zona de mezclado',
    'INOXPA',
    'MIX-500',
    'INO-MEZ-001',
    'OPERATIVA'
)

ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    area = VALUES(area),
    ubicacion = VALUES(ubicacion),
    fabricante = VALUES(fabricante),
    modelo = VALUES(modelo),
    estado = VALUES(estado);



-- =========================================================
-- PIEZAS - BOMBA CENTRÍFUGA
-- =========================================================

INSERT INTO pieza
(
    id_maquina,
    codigo,
    nombre,
    descripcion,
    ubicacion,
    fabricante,
    referencia,
    estado,
    codigo_qr
)
VALUES

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-BOM-001'),
    'PZA-BOM-001',
    'Motor eléctrico',
    'Motor principal de accionamiento de la bomba.',
    'Parte superior',
    'WEG',
    'W22-5HP',
    'OPERATIVA',
    'QR-PZA-BOM-001'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-BOM-001'),
    'PZA-BOM-002',
    'Sello mecánico',
    'Sello encargado de evitar fugas en el eje.',
    'Eje de bomba',
    'John Crane',
    'JC-2100',
    'OPERATIVA',
    'QR-PZA-BOM-002'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-BOM-001'),
    'PZA-BOM-003',
    'Rodamiento delantero',
    'Rodamiento del eje principal.',
    'Soporte delantero',
    'SKF',
    '6205-2RS',
    'OPERATIVA',
    'QR-PZA-BOM-003'
);


-- =========================================================
-- PIEZAS - COMPRESOR
-- =========================================================

INSERT INTO pieza
(
    id_maquina,
    codigo,
    nombre,
    descripcion,
    ubicacion,
    fabricante,
    referencia,
    estado,
    codigo_qr
)
VALUES

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-COMP-001'),
    'PZA-COMP-001',
    'Filtro de aire',
    'Filtro de entrada del compresor.',
    'Entrada de aire',
    'Atlas Copco',
    'AF-001',
    'OPERATIVA',
    'QR-PZA-COMP-001'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-COMP-001'),
    'PZA-COMP-002',
    'Correa de transmisión',
    'Correa de transmisión del motor.',
    'Compartimiento lateral',
    'Gates',
    'GT-500',
    'OPERATIVA',
    'QR-PZA-COMP-002'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-COMP-001'),
    'PZA-COMP-003',
    'Válvula de seguridad',
    'Válvula de alivio de presión.',
    'Salida del tanque',
    'SMC',
    'SV-20',
    'OPERATIVA',
    'QR-PZA-COMP-003'
);


-- =========================================================
-- PIEZAS - ENVASADORA
-- =========================================================

INSERT INTO pieza
(
    id_maquina,
    codigo,
    nombre,
    descripcion,
    ubicacion,
    fabricante,
    referencia,
    estado,
    codigo_qr
)
VALUES

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-ENV-001'),
    'PZA-ENV-001',
    'Sensor fotoeléctrico',
    'Sensor para detección de envases.',
    'Entrada de envases',
    'SICK',
    'WTB4',
    'OPERATIVA',
    'QR-PZA-ENV-001'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-ENV-001'),
    'PZA-ENV-002',
    'Banda transportadora',
    'Banda principal de transporte.',
    'Zona central',
    'Habasit',
    'HAB-220',
    'OPERATIVA',
    'QR-PZA-ENV-002'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-ENV-001'),
    'PZA-ENV-003',
    'Cilindro neumático',
    'Cilindro utilizado para posicionamiento de envases.',
    'Módulo de llenado',
    'Festo',
    'DSNU-25',
    'OPERATIVA',
    'QR-PZA-ENV-003'
);


-- =========================================================
-- PIEZAS - ETIQUETADORA
-- =========================================================

INSERT INTO pieza
(
    id_maquina,
    codigo,
    nombre,
    descripcion,
    ubicacion,
    fabricante,
    referencia,
    estado,
    codigo_qr
)
VALUES

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-ETQ-001'),
    'PZA-ETQ-001',
    'Rodillo de arrastre',
    'Rodillo encargado del avance de etiquetas.',
    'Cabezal de etiquetado',
    'Zebra',
    'ROL-110',
    'MANTENIMIENTO',
    'QR-PZA-ETQ-001'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-ETQ-001'),
    'PZA-ETQ-002',
    'Sensor de etiqueta',
    'Sensor encargado de detectar posición de etiqueta.',
    'Cabezal',
    'Zebra',
    'SEN-220',
    'OPERATIVA',
    'QR-PZA-ETQ-002'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-ETQ-001'),
    'PZA-ETQ-003',
    'Motor paso a paso',
    'Motor para desplazamiento del sistema de etiquetado.',
    'Interior del cabezal',
    'NEMA',
    'NEMA23',
    'OPERATIVA',
    'QR-PZA-ETQ-003'
);


-- =========================================================
-- PIEZAS - TANQUE MEZCLADOR
-- =========================================================

INSERT INTO pieza
(
    id_maquina,
    codigo,
    nombre,
    descripcion,
    ubicacion,
    fabricante,
    referencia,
    estado,
    codigo_qr
)
VALUES

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-MEZ-001'),
    'PZA-MEZ-001',
    'Motor agitador',
    'Motor principal del sistema de agitación.',
    'Parte superior',
    'Siemens',
    'SIM-5HP',
    'OPERATIVA',
    'QR-PZA-MEZ-001'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-MEZ-001'),
    'PZA-MEZ-002',
    'Eje agitador',
    'Eje de transmisión del agitador.',
    'Interior del tanque',
    'INOXPA',
    'EJE-500',
    'OPERATIVA',
    'QR-PZA-MEZ-002'
),

(
    (SELECT id_maquina FROM maquina WHERE codigo = 'MAQ-MEZ-001'),
    'PZA-MEZ-003',
    'Aspas de agitación',
    'Conjunto de aspas para mezcla del producto.',
    'Interior inferior',
    'INOXPA',
    'ASP-500',
    'OPERATIVA',
    'QR-PZA-MEZ-003'
);