const staticPedidos = "pedidos-tpi-v1"
const assets = [
  "/",
  "/login",
  "/Cliente/RealizarPedido",
  "/Cliente/RegistrosPedidos",
  "/Cliente/verCarrito",
  "/Cliente/RegistrosPedidos",
  "/repartidor",

  "/css/style.css",
  "/css/bootstrap.min.css",
  "/js/app.js",
  "/js/bootstrap.bundle.min.js",
  "/js/popper.min.js",


]

self.addEventListener("install", installEvent => {
  installEvent.waitUntil(
    caches.open(staticPedidos).then(cache => {
      cache.addAll(assets)
    })
  )
})
self.addEventListener("fetch", fetchEvent => {
  fetchEvent.respondWith(
    caches.match(fetchEvent.request).then(res => {
      return res || fetch(fetchEvent.request)
    })
  )
})