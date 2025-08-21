INSERT INTO identification_type (id, name, code, country_code, by_default, translation)
VALUES
(1, 'CUIT', '80', 'ARG', 0, 'unique_tax_identification_code'),
(2, 'CUIL', '86', 'ARG', 0, 'unique_labor_identification_code'),
(3, 'CDI', '87', 'ARG', 0, 'individual_taxpayer_registry'),
(4, 'LC', '90', 'ARG', 0, 'citizen_card'),
(5, 'CI', '91', 'ARG', 0, NULL),
(6, 'Pasaporte', '94', 'ARG', 0, 'passport'),
(7, 'DNI', '96', 'ARG', 0, 'national_identity_document'),
(8, 'Sin identificar/venta global diaria', '99', 'ARG', 0, 'daily_global_sales');

INSERT INTO tax_situation (id, name, code, country_code, by_default, translation)
VALUES
(1, 'Responsable Inscripto', '1', 'ARG', 0, 'registered_responsible'),
(2, 'Monotributo', '6', 'ARG', 0, 'simplified_tax_regime'),
(3, 'Exento', '4', 'ARG', 0, 'exempt'),
(4, 'Consumidor Final', '5', 'ARG', 0, 'final_consumer');

-- Inserción ajustada con valores válidos
INSERT INTO Customer (code, name, tax_residence, type, doc_number, nickname, comercial_activity,
between_address1, between_address2, lat, lng, portal_password, extra1, extra2, temporary, status, city)
VALUES
('1', 'John', 'USA', 'invoice', '12345678', 'johnny', 'IT Business', 'First Ave', 'Second Ave', '40.7128', '-74.0060', 'postal123', 'extra info', 'alias', FALSE, 'enabled', 'Rosario'),
('2', 'Juan', 'Argentina', 'no_fiscal_invoice', '8779974', 'juanito', 'Retail', 'España', 'Dorrego', '34.6037', '-58.3816', 'postal456', 'extra info', 'alias', FALSE, 'enabled', 'Rosario'),
('3', 'Maria', 'Mexico', 'invoice', '99887766', 'mary', 'Marketing', 'Reforma', 'Insurgentes', '19.4326', '-99.1332', 'pass789', 'extra', 'alias', FALSE, 'enabled', 'CDMX'),
('4', 'Carlos', 'Colombia', 'invoice', '11223344', 'charly', 'Construction', 'Carrera 7', 'Calle 45', '4.6097', '-74.0817', 'pass123', 'extra', 'alias', FALSE, 'enabled', 'Bogotá'),
('5', 'Sofia', 'Chile', 'invoice', '55667788', 'sofi', 'Real Estate', 'Providencia', 'Las Condes', '-33.4489', '-70.6693', 'pass456', 'extra', 'alias', FALSE, 'enabled', 'Santiago'),
('6', 'Pedro', 'Brazil', 'no_fiscal_invoice', '66554433', 'pedrito', 'Tourism', 'Avenida Paulista', 'Bela Vista', '-23.5505', '-46.6333', 'pass999', 'extra', 'alias', FALSE, 'enabled', 'São Paulo'),
('7', 'Lucia', 'Spain', 'invoice', '33445566', 'luci', 'Finance', 'Gran Vía', 'Castellana', '40.4168', '-3.7038', 'pass888', 'extra', 'alias', FALSE, 'enabled', 'Madrid'),
('8', 'Miguel', 'Peru', 'invoice', '44556677', 'mike', 'Mining', 'Avenida Javier Prado', 'Avenida Arequipa', '-12.0464', '-77.0428', 'pass777', 'extra', 'alias', FALSE, 'enabled', 'Lima'),
('9', 'Fernanda', 'Ecuador', 'no_fiscal_invoice', '22334455', 'fer', 'Education', 'Amazonas', 'Shyris', '-0.1807', '-78.4678', 'pass666', 'extra', 'alias', FALSE, 'enabled', 'Quito'),
('10', 'Antonio', 'Bolivia', 'invoice', '99882277', 'tony', 'Logistics', 'Avenida Camacho', '20 de Octubre', '-16.5000', '-68.1500', 'pass555', 'extra', 'alias', FALSE, 'enabled', 'La Paz'),
('11', 'Valentina', 'Paraguay', 'invoice', '88997766', 'valen', 'Food Industry', 'Avenida Mariscal López', 'Avenida España', '-25.2637', '-57.5759', 'pass444', 'extra', 'alias', FALSE, 'enabled', 'Asunción'),
('12', 'Ricardo', 'Uruguay', 'invoice', '66553322', 'richi', 'Agriculture', '18 de Julio', 'Ejido', '-34.9011', '-56.1645', 'pass333', 'extra', 'alias', FALSE, 'enabled', 'Montevideo'),
('13', 'Gabriela', 'Venezuela', 'no_fiscal_invoice', '22335577', 'gaby', 'Oil Industry', 'Altamira', 'Chacao', '10.4806', '-66.9036', 'pass222', 'extra', 'alias', FALSE, 'enabled', 'Caracas'),
('14', 'Francisco', 'Panama', 'invoice', '11224466', 'fran', 'Banking', 'Calle 50', 'Vía España', '8.9833', '-79.5167', 'pass111', 'extra', 'alias', FALSE, 'enabled', 'Panamá'),
('15', 'Elena', 'Costa Rica', 'invoice', '77889900', 'ele', 'Eco Tourism', 'Avenida Central', 'Paseo Colón', '9.9281', '-84.0907', 'pass000', 'extra', 'alias', FALSE, 'enabled', 'San José'),
('16', 'Hugo', 'Dominican Republic', 'no_fiscal_invoice', '55668899', 'hugo', 'Hospitality', 'Malecón', 'El Conde', '18.4861', '-69.9312', 'pass321', 'extra', 'alias', FALSE, 'enabled', 'Santo Domingo'),
('17', 'Andrea', 'Honduras', 'invoice', '99886655', 'andy', 'Telecom', 'Boulevard Morazán', 'Avenida La Paz', '14.0723', '-87.1921', 'pass654', 'extra', 'alias', FALSE, 'enabled', 'Tegucigalpa');



-- Inserción de teléfonos
INSERT INTO customer_phones (customer_id, phone)
VALUES
    (1, '555-1234'),
    (1, '555-5678'),
    (2, '555-8111'),
    (2, '555-7842'),
    (3, '555-2222'),
    (4, '555-3333'),
    (4, '555-4444'),
    (4, '555-5555'),
    (5, '555-6666'),
    (6, '555-7777'),
    (6, '555-8888'),
    (7, '555-9999'),
    (8, '555-0000'),
    (8, '555-1111'),
    (9, '555-2223'),
    (9, '555-2224'),
    (10, '555-3334'),
    (11, '555-4445'),
    (12, '555-5556'),
    (13, '555-6667'),
    (13, '555-6668'),
    (14, '555-7779'),
    (15, '555-8880'),
    (16, '555-9991'),
    (16, '555-9992'),
    (17, '555-0002');

-- Inserción de correos electrónicos
INSERT INTO customer_emails (customer_id, email)
VALUES
    (1, 'john.doe@example.com'),
    (1, 'j.doe@company.com'),
    (2, 'juan.perez@example.com'),
    (2, 'j.perez@company.com'),
    (3, 'maria.lopez@example.com'),
    (3, 'm.lopez@company.com'),
    (4, 'carlos.rios@example.com'),
    (5, 'sofia.martinez@example.com'),
    (5, 'sofia.m@realestate.com'),
    (5, 'sofi@chile.com'),
    (6, 'pedro.silva@example.com'),
    (6, 'pedro@tourismbr.com'),
    (7, 'lucia.rodriguez@example.com'),
    (8, 'miguel.fernandez@example.com'),
    (8, 'mike@miningperu.com'),
    (9, 'fernanda.torres@example.com'),
    (9, 'fer.torres@education.com'),
    (10, 'antonio.gomez@example.com'),
    (11, 'valentina.ramirez@example.com'),
    (12, 'ricardo.mendez@example.com'),
    (12, 'richi@uruguayagri.com'),
    (13, 'gabriela.ortiz@example.com'),
    (14, 'francisco.luna@example.com'),
    (14, 'fran@bankingpa.com'),
    (15, 'elena.sosa@example.com'),
    (16, 'hugo.martinez@example.com'),
    (16, 'hugo@hospitalitydr.com'),
    (17, 'andrea.vargas@example.com');

-- Inserción de direcciones
INSERT INTO customer_addresses (customer_id, address)
VALUES
    (1, 'Direccion1'),
    (1, 'Direccion2'),
    (2, 'Direccion3'),
    (2, 'Direccion4'),
    (3, 'Direccion5'),
    (3, 'Direccion6'),
    (3, 'Direccion7'),
    (4, 'Direccion8'),
    (5, 'Direccion9'),
    (5, 'Direccion10'),
    (6, 'Direccion11'),
    (6, 'Direccion12'),
    (7, 'Direccion13'),
    (8, 'Direccion14'),
    (8, 'Direccion15'),
    (8, 'Direccion16'),
    (9, 'Direccion17'),
    (9, 'Direccion18'),
    (10, 'Direccion19'),
    (11, 'Direccion20'),
    (12, 'Direccion21'),
    (12, 'Direccion22'),
    (13, 'Direccion23'),
    (14, 'Direccion24'),
    (14, 'Direccion25'),
    (15, 'Direccion26'),
    (16, 'Direccion27'),
    (16, 'Direccion28'),
    (17, 'Direccion29');




--INSERT TICKET CATEGORY

INSERT INTO ticket_categories (
    id, name, comment, amount, color, colortext, view_customer, cat_tickets, cat_op, created_at, updated_at, discontinued, translation
) VALUES
(1, 'Mudanza', NULL, 0.00, '#9494EA', NULL, '1', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(2, 'Sin Servicio', NULL, 0.00, '#7CACD0', NULL, '1', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(3, 'Cambio de Plan', NULL, 0.00, '#71BE90', NULL, '1', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(4, 'Instalacion', 'Instalaciones Nuevas', 8000.00, '#fbff00', '#000000', '', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(5, 'Servicio Tecnico FIBRA', 'Servicio Tecnico FIBRA', 0.00, '#ff0000', '#000000', '', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(6, 'DESINSTALACIONES FIBRA', 'DESINSTALACIONES FIBRA', 0.00, '#08fd24', '#000000', '', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(7, 'Espera ampliacion', 'espera ampliacion', 0.00, '#00047a', '#f8f7f7', '', 1, 0, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(8, 'MOROSOS', 'MOROSOS', 0.00, '#ffccec', '#fefbfb', '', 1, 0, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(9, 'DESINSTALACIONES ANTENA', 'DESINSTALACIONES ANTENA', 0.00, '#015b50', '#000000', '', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(10, 'Servicio Tecnico ANTENA', 'Servicio Técnico ANTENA', 0.00, '#d9c6eb', '#000000', '', 1, 1, '2023-10-18 07:21:42', '2023-10-18 07:21:42', 0, NULL),
(11, 'DEUDORES', 'DEUDORES', 0.00, '#F57F0E', NULL, '1', 1, 1, '2024-06-17 21:48:53', '2024-06-17 21:48:53', 0, NULL),
(12, 'ESPERA AMPLIACION NUEVA', NULL, 0.00, '#352B2D', NULL, '1', 1, 0, '2024-09-19 10:53:06', '2024-09-19 10:53:06', 0, NULL);

-- INSERT TICKET PRIORITY
INSERT INTO ticket_priorities (
    id, name, created_at, updated_at, color
) VALUES
(1, 'Normal', '2023-10-18 07:21:42', '2023-10-18 07:21:42', NULL),
(2, 'Urgente', '2023-10-18 07:21:42', '2023-10-18 07:21:42', NULL),
(3, 'Baja', '2023-10-18 07:21:42', '2023-10-18 07:21:42', NULL),
(4, 'Alta', '2023-10-18 08:00:16', NULL, NULL);

--INSERT TICKET STATUS
INSERT INTO ticket_statuses (
    id, name, color, created_at, updated_at
) VALUES
(1, 'Abierto', '#E8C121', '2023-10-18 07:21:42', '2023-10-18 07:21:42'),
(2, 'Pendiente', '#E692BC', '2023-10-18 07:21:42', '2023-10-18 07:21:42'),
(3, 'Cerrado', '#E2AE5B', '2023-10-18 07:21:42', '2023-10-18 07:21:42');

--INSERT TICKET AREA
INSERT INTO ticket_areas (
    id, name, created_at, updated_at
) VALUES
(1, 'Soporte', '2023-10-18 07:21:42', '2023-10-18 07:21:42'),
(2, 'Ventas', '2023-10-18 07:21:42', '2023-10-18 07:21:42'),
(3, 'Administracion', '2023-10-18 07:21:42', '2023-10-18 07:21:42'),
(4, 'marcelo', '2023-10-18 08:00:15', NULL),
(5, 'cuadrilla gabriel', '2023-10-18 08:00:15', NULL),
(6, 'ema y andres', '2023-10-18 08:00:15', NULL),
(7, 'agus y angel', '2023-10-18 08:00:15', NULL),
(8, 'Atencion al Publico', '2023-10-18 08:00:15', NULL);


-- INSERT PLAN
INSERT INTO plan (name, price)
VALUES
    ('50_HOGAR', 13800.0),
    ('50_SIMETRICO', 14100.0),
    ('100_HOGAR', 14750.0),
    ('100_SIMETRICO', 15150.0),
    ('200_HOGAR', 15750.0),
    ('200_SIMETRICO', 16050.0),
    ('500_HOGAR', 19250.0),
    ('500_SIMETRICO', 19750.0);

-- INSERT BOX
INSERT INTO box (id, box_number, port_quantity, comments, power, address, active, aviable_port)
VALUES
    (1, '1001', 16, 'Caja principal para conexiones del edificio A', 220, 'Calle 123, Ciudad A', true, 16),
    (2, '1002', 8, 'Caja secundaria para conexiones del edificio B', 220, 'Calle 456, Ciudad B)', true, 8),
    (3, '1003', 16, 'Caja de respaldo para conexiones de emergencia', 380, 'Calle 789, Ciudad C', true, 16),
    (4, '1004', 8, 'Caja para conexiones de telecomunicaciones', 110, 'Calle 101, Ciudad D', true, 8),
    (5, '1005', 16, 'Caja para conexiones de red de oficina', 220, 'Calle 202, Ciudad E', false, 16);

-- Insertar puertos para cada caja
-- Caja 1
INSERT INTO Port (position, is_active, box_id)
VALUES
    (1, b'0', 1),
    (2, b'0', 1),
    (3, b'0', 1),
    (4, b'0', 1),
    (5, b'0', 1),
    (6, b'0', 1),
    (7, b'0', 1),
    (8, b'0', 1),
    (9, b'0', 1),
    (10, b'0', 1),
    (11, b'0', 1),
    (12, b'0', 1),
    (13, b'0', 1),
    (14, b'0', 1),
    (15, b'0', 1),
    (16, b'0', 1);

-- Caja 2
INSERT INTO Port (position, is_active, box_id)
VALUES
    (1, b'0', 2),
    (2, b'0', 2),
    (3, b'0', 2),
    (4, b'0', 2),
    (5, b'0', 2),
    (6, b'0', 2),
    (7, b'0', 2),
    (8, b'0', 2);


-- Caja 3
INSERT INTO Port (position, is_active, box_id)
VALUES
    (1, b'0', 3),
    (2, b'0', 3),
    (3, b'0', 3),
    (4, b'0', 3),
    (5, b'0', 3),
    (6, b'0', 3),
    (7, b'0', 3),
    (8, b'0', 3),
    (9, b'0', 3),
    (10, b'0', 3),
    (11, b'0', 3),
    (12, b'0', 3),
    (13, b'0', 3),
    (14, b'0', 3),
    (15, b'0', 3),
    (16, b'0', 3);

-- Caja 4
INSERT INTO Port (position, is_active, box_id)
VALUES
    (1, b'0', 4),
    (2, b'0', 4),
    (3, b'0', 4),
    (4, b'0', 4),
    (5, b'0', 4),
    (6, b'0', 4),
    (7, b'0', 4),
    (8, b'0', 4);


-- Caja 5
INSERT INTO Port (position, is_active, box_id)
VALUES
    (1, b'0', 5),
    (2, b'0', 5),
    (3, b'0', 5),
    (4, b'0', 5),
    (5, b'0', 5),
    (6, b'0', 5),
    (7, b'0', 5),
    (8, b'0', 5),
    (9, b'0', 5),
    (10, b'0', 5),
    (11, b'0', 5),
    (12, b'0', 5),
    (13, b'0', 5),
    (14, b'0', 5),
    (15, b'0', 5),
    (16, b'0', 5);

    --permisos
    INSERT INTO permission (name, description) VALUES
    ('READ', 'Permiso para leer datos'),
    ('CREATE', 'Permiso para crear datos'),
    ('UPDATE', 'Permiso para actualizar datos'),
    ('DELETE', 'Permiso para eliminar datos');

    --roles
    INSERT INTO role (name) VALUES
    ('SUPERADMIN'),
    ('ADMIN'),
    ('TECHNICAL'),
    ('OPERATOR'),
    ('CUSTOMER');


    --relacion ROLES/PERMISOS
    -- Obtener IDs de permisos
    SELECT id, name FROM permission;

    -- Obtener IDs de roles
    SELECT id, name FROM role;

    -- Relacionar SUPERADMIN con todos los permisos
    INSERT INTO role_permission (role_id, permission_id)
    SELECT r.id, p.id
    FROM role r, permission p
    WHERE r.name = 'SUPERADMIN';

    -- Relacionar ADMIN con todos los permisos excepto la gestión de SUPERADMIN
    INSERT INTO role_permission (role_id, permission_id)
    SELECT r.id, p.id
    FROM role r, permission p
    WHERE r.name = 'ADMIN';

    -- Relacionar TECHNICAL solo con permiso de lectura
    INSERT INTO role_permission (role_id, permission_id)
    SELECT r.id, p.id
    FROM role r, permission p
    WHERE r.name = 'TECHNICAL' AND p.name = 'READ';

    -- Relacionar OPERATOR con lectura, creación y actualización
    INSERT INTO role_permission (role_id, permission_id)
    SELECT r.id, p.id
    FROM role r, permission p
    WHERE r.name = 'OPERATOR' AND p.name IN ('READ', 'CREATE', 'UPDATE');

    -- Relacionar CUSTOMER solo con permiso de lectura
    INSERT INTO role_permission (role_id, permission_id)
    SELECT r.id, p.id
    FROM role r, permission p
    WHERE r.name = 'CUSTOMER' AND p.name = 'READ';

   --users
   -- Inserción de datos en la tabla `user`
   INSERT INTO `user` (username, name, password, email, address, phone, role_id)
   VALUES
   ('superAdmin', 'katy perry', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'katyperry@example.com', 'Calle Ficticia 123', '123-456-7890', 1),
   ('admin', 'Dua Lipa', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'dualipa@example.com', 'Avenida Siempre Viva 456', '098-765-4321', 2),
   ('technical', 'Mengano technical', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'technical@example.com', 'Paseo del Sol 789', '112-233-4455', 3),
   ('operator', 'Roberto robertito', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'operator@example.com', 'Sorrento 756', '112-233-4455', 4),
   ('customer', 'Iron man', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'customer@example.com', 'Los angeles 256', '112-233-4455', 5);


-- Inserción de historial de clientes
INSERT INTO history (date, module, action, comment, from_state, to_state, user_id, customer_id, ticket_id)
VALUES
    ('2024-12-13T15:55:17.2144853', 'CUSTOMER', 'CUSTOMER_CREATED', '-', '','', 1, 1, null),
    ('2024-12-13T15:55:17.2144853', 'CUSTOMER', 'CUSTOMER_CREATED', '-','','', 2, 2, null);

