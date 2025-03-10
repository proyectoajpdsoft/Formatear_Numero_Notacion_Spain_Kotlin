
//Formatea un número para devolver separador de miles y decimal en ES
fun FormatearNumeroSeparadorMiles(numero: Double, moneda: String = "€"): String {
    try {
        val formato = NumberFormat.getNumberInstance(Locale("es", "ES"))
        formato.minimumFractionDigits = 2
        formato.maximumFractionDigits = 2
 
        var numFormateado = formato.format(numero) + moneda
        return  numFormateado
    } catch (e: Exception) {
        return "0,00"
    }
}

@Composable
fun LibroVista(libroVistaModelo: LibroVistaModelo, context: Context) {
    val state = libroVistaModelo.state
    if (state.estaCargando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        stickyHeader {
            Text(
                "Facturas",
                fontSize = 14.sp
            )
        }
        itemsIndexed(items = facturaVistaModelo.response) { _, item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .background(Color.White)
                    .clickable {
                        val mensaje = "Se ha pulsado en la factura " + item.codigo.toString()
                        EscribirLog(mensaje = mensaje)
                        MostrarMensaje(context, mensaje, tiempoLargo = true)
                    }
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "[" + item.codigo.toString() + "] " + item.titulo.toString(),
                        fontSize = 13.sp
                    )
                    Text(
                        " → Fecha: " + item.fecha_compra?.let {
                            SimpleDateFormat("dd-MM-yyyy").format(
                                it
                            )
                        },
                        fontSize = 12.sp
                    )
                    Text(
                        text = " → Importe: " + item.precio?.let {
                            FormatearNumeroSeparadorMiles(
                                it
                            )
                        },
                        fontSize = 11.sp
                    )
                }
            }
        }
    }