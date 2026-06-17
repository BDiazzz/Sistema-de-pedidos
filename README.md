# Sistema de Gestión de Pedidos y Logística (GPDRL)

Este repositorio contiene un sistema web robusto y monolítico para la automatización, administración y despacho de pedidos comerciales. La aplicación integra un backend empresarial desarrollado con **Spring Boot**, persistencia de datos relacional y una interfaz de usuario dinámica estructurada mediante arquitecturas de plantillas del lado del servidor, complementada con capacidades móviles básicas (PWA).

El sistema cuenta con un control de accesos basado en roles distribuidos en tres grandes flujos: **Clientes** (autogestión de compras y lealtad), **Administración/Empleados** (control operativo y despachos) y **Repartidores** (logística de entregas en tiempo real).

---

## 🚀 Características Principales

### 📦 Gestión de Pedidos y Logística
* **Flujo Completo de Orden:** Procesamiento desde el carrito de compras del cliente hasta la facturación y asignación de despachos.
* **Módulo de Repartidores:** Gestión logística y seguimiento de estados de órdenes en curso (`EstadoOrden`).
* **Administración de Menú y Productos:** Catálogos dinámicos con soporte para almacenamiento externo.

### 🛡️ Seguridad Avanzada y Control de Acceso
* **Autenticación Basada en JWT:** Validación estricta mediante tokens con interceptores dedicados (`JwtTokenValidator`, `JwtUtils`).
* **Seguridad por Roles:** Restricciones de seguridad a nivel de métodos y controladores para proteger endpoints sensibles (`Rol`, `Permiso`).
* **Generación Segura:** Componentes para el cifrado y generación automatizada de credenciales temporales (`PasswordGenerator`).

### 💎 Programa de Lealtad y Fidelización
* **Sistema de Puntos:** Acumulación automatizada por transacciones completadas con éxito.
* **Cupones de Descuento:** Generación, validación de fechas de vigencia y canje de cupones con tablas de auditoría (`RegistroCupon`).

### 🔌 Integración de Servicios Externos
* **Google Drive API:** Integración en la capa de servicios (`GoogleDriverService`) para la persistencia dinámica de recursos multimedia e imágenes del menú.
* **Notificaciones por Correo:** Servicio dedicado para el envío automático de notificaciones del sistema y flujos de recuperación de contraseñas (`envioCorreo`).

---

## 🛠️ Tecnologías y Herramientas Utilizadas

* **Backend Core:** Java, Spring Boot (Spring Security, Spring Data JPA).
* **Gestión de Dependencias:** Maven (incluye `mvnw` Maven Wrapper).
* **Base de Datos:** PostgreSQL (Soporte relacional completo para entidades indexadas).
* **Frontend:** HTML5 (Templates con herencia de plantillas dinámicas), CSS3, JavaScript Vanila estructurado de forma modular.
* **Capacidad Offline/PWA:** Configuración base mediante `manifest.json` y `service-workers.js`.

---

## 📂 Arquitectura y Estructura del Proyecto

El backend adopta un enfoque de diseño modular guiado por dominios funcionales. Cada módulo agrupa su propia estructura de controladores de API, repositorios de datos y lógica de negocio específica:

```text
src/main/java/com/tpi/gpdrl/
├── GpdrlApplication.java            # Clase principal de arranque de Spring Boot
├── Entity/                          # Modelo relacional único de la base de datos
│   ├── Usuario.java, Cliente.java, Pedido.java, Factura.java, Producto.java, etc.
├── Administracion/                  # Control operativo de recursos humanos y personal
│   └── Controller, Repository, Service (Empleado)
├── Cliente/                         # Endpoints públicos y lógicas del flujo de compradores
│   └── Controller (Pedido, Ubicación), Repository, Service, CarritoDTO
├── Menu/                            # Catálogos, inventarios y almacenamiento en la nube
│   └── Controller, Repository, Service (GoogleDriverService, Producto)
├── Pedido/                          # Motor de asignación de logística y despacho
│   └── Controller, Repository, Service (Asignacion, DetallePedido)
├── Programa_lealtad/                # Reglas de fidelización de clientes
│   └── Controller, Repository, Service (Cupon, RegistroCupon)
├── Puntos/                          # Control de billeteras de puntos acumulativos
│   └── Controller, Repository, Service
├── Repartidor/                      # Monitoreo de entregas físicas de pedidos en ruta
│   └── Controller, Repository, Service
└── Seguridad/                       # Infraestructura crítica de autenticación y cifrado
    ├── Clase/                       # Utils (envioCorreo, PasswordGenerator)
    ├── Configuraciones/             # Filtros de filtros JWT y Custom Authentication Handlers
    └── Controlador, Repository, Service (Session, Usuario)
