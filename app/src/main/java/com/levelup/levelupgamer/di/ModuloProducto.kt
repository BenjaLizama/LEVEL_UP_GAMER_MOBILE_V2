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
                    imagenURL = "https://cdn.awsli.com.br/684/684977/produto/290262631/poli-m424h115z2.jpg",
                    descripcion = "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.",
                    categoria = "Juegos de Mesa"
                ))
                productoDao.insertarProducto(Producto(
                    id = 2,
                    nombre = "Carcassonne",
                    precio = 24990.0,
                    imagenURL = "https://preview.redd.it/an-old-keyboard-from-work-v0-4heixs24c54e1.jpeg?auto=webp&s=d34fd11a7aa2208e98f5df98b724dd8ef23aa4fc",
                    descripcion = "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.",
                    categoria = "Juegos de Mesa"
                ))
                productoDao.insertarProducto(Producto(
                    id = 3,
                    nombre = "Mouse RGB",
                    precio = 45.00,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
                    categoria = "Accesorios"
                ))
                productoDao.insertarProducto(Producto(
                    id = 4,
                    nombre = "Auriculares Gamer HyperX Cloud II",
                    precio = 79990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.",
                    categoria = "Accesorios"
                ))
                productoDao.insertarProducto(Producto(
                    id = 5,
                    nombre = "PlayStation 5",
                    precio = 549990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
                    categoria = "Consolas"
                ))
                productoDao.insertarProducto(Producto(
                    id = 7,
                    nombre = "PC Gamer ASUS ROG Strix",
                    precio = 1299990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.",
                    categoria = "Computadores Gamers"
                ))
                productoDao.insertarProducto(Producto(
                    id = 8,
                    nombre = "Sillas Gamers",
                    precio = 349990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.",
                    categoria = "Sillas Gamers"
                ))
                productoDao.insertarProducto(Producto(
                    id = 9,
                    nombre = "Mouse Gamer Logitech G502 HERO",
                    precio = 49990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Con sensor de alta precisión y botones\n" +
                            "personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.",
                    categoria = "Mouse"
                ))
                productoDao.insertarProducto(Producto(
                    id = 10,
                    nombre = "Mousepad Razer Goliathus Extended Chroma",
                    precio = 29990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.",
                    categoria = "Mousepad"
                ))
                productoDao.insertarProducto(Producto(
                    id = 11,
                    nombre = "Polera Gamer Personalizada 'Level-Up'",
                    precio = 14990.0,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.",
                    categoria = "Poleras Personalizadas"
                ))
            }
        }
    }