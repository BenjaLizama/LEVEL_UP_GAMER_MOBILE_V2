package com.levelup.levelupgamer.di

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.levelup.levelupgamer.db.dao.ProductoDAO
import com.levelup.levelupgamer.db.entidades.Producto
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModuloProducto(private val productoDaoProvider: Provider<ProductoDAO>) :
    RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val productoDao = productoDaoProvider.get()

                productoDao.insertarProducto(Producto(
                    id = 1,
                    nombre = "Catan",
                    precio = 29990.0,
                    imagenURL = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQtZWnoL7CtxLIcPUxKQIvM96_i-JEuUIHtpMYmu0q4Oj3mpJsx0EavR1Q-Vw6giKPgcf9zhILFvRyyhrgwsENmKtCefj8wUYDHm5k9xYjOEjNMUjlcLMAi1g",
                    descripcion = "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.",
                    categoria = "Juegos de Mesa"
                ))
                productoDao.insertarProducto(Producto(
                    id = 2,
                    nombre = "Carcassonne",
                    precio = 24990.0,
                    imagenURL = "https://m.media-amazon.com/images/I/91OlUan3whL.jpg",
                    descripcion = "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.",
                    categoria = "Juegos de Mesa"
                ))
                productoDao.insertarProducto(Producto(
                    id = 3,
                    nombre = "Control Inalambrico Xbox Series X",
                    precio = 59990.0,
                    imagenURL = "https://http2.mlstatic.com/D_NQ_NP_2X_746748-MLA95652415350_102025-F.webp",
                    descripcion = "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
                    categoria = "Accesorios"
                ))
                productoDao.insertarProducto(Producto(
                    id = 4,
                    nombre = "Auriculares Gamer HyperX Cloud II",
                    precio = 79990.0,
                    imagenURL = "https://row.hyperx.com/cdn/shop/files/hyperx_cloud_ii_red_1_main.jpg?v=1737720332",
                    descripcion = "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.",
                    categoria = "Accesorios"
                ))
                productoDao.insertarProducto(Producto(
                    id = 5,
                    nombre = "PlayStation 5",
                    precio = 549990.0,
                    imagenURL = "https://clsonyb2c.vtexassets.com/arquivos/ids/465172-800-800?v=638658958190900000&width=800&height=800&aspect=true",
                    descripcion = "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
                    categoria = "Consolas"
                ))
                productoDao.insertarProducto(Producto(
                    id = 7,
                    nombre = "PC Gamer ASUS ROG Strix",
                    precio = 1299990.0,
                    imagenURL = "https://sipoonline.cl/wp-content/uploads/2024/05/Pc-Gamer-Asus-Strix_Intel-Core-i9-14900KF-64GB-DDR5-5600mhz-RTX-4090-24GB.png",
                    descripcion = "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.",
                    categoria = "Computadores Gamers"
                ))
                productoDao.insertarProducto(Producto(
                    id = 8,
                    nombre = "Silla Gamer Secretlab Titan",
                    precio = 349990.0,
                    imagenURL = "https://images-na.ssl-images-amazon.com/images/I/41vyYB3rS9L.jpg",
                    descripcion = "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.",
                    categoria = "Sillas Gamers"
                ))
                productoDao.insertarProducto(Producto(
                    id = 9,
                    nombre = "Mouse Gamer Logitech G502 HERO",
                    precio = 49990.0,
                    imagenURL = "https://media.falabella.com/falabellaCL/142374128_01/w=1500,h=1500,fit=pad",
                    descripcion = "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.",
                    categoria = "Mouse"
                ))
                productoDao.insertarProducto(Producto(
                    id = 10,
                    nombre = "Mousepad Razer Goliathus Extended Chroma",
                    precio = 29990.0,
                    imagenURL = "https://www.centec.cl/cdn/shop/files/open-uri20220520-20794-kc6gvb_1200x1200_crop_center.jpg?v=1736799229",
                    descripcion = "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.",
                    categoria = "Mousepad"
                ))
                productoDao.insertarProducto(Producto(
                    id = 11,
                    nombre = "Polera Gamer Personalizada 'Level-Up'",
                    precio = 14990.0,
                    imagenURL = "https://storage.googleapis.com/sites-files/1012/sites_products-63337bdd9e841.jpg",
                    descripcion = "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.",
                    categoria = "Poleras Personalizadas"
                ))
            }
        }
    }