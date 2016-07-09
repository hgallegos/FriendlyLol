package example.hans.friendlylol.model;

/*
*           ESTA CLASE NO ES NECESARIA !!
* ES SOLO DE PRUEBA PARA OBTENER UN LISTA PARA MOSRTRAR EN LOS CARDVIEW DE LOS TABS LAYOUTS
*/

public class VersionModel {
    public String name;

    public static final String[] data = {
            "Cupcake ...VERSION MODEL... Cupcake Cupcake Cupcake Cupcake Cupcake Cupcake",
            "Donut ...VERSION MODEL... bla bla este es un texto de prueba para ver como se comporta el cardView aaaaaaaaaaaaaaaaaaaaaaaah",
            "Eclair ...VERSION MODEL...",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Icecream Sandwich",
            "Jelly Bean",
            "Kitkat",
            "Lollipop"
    }; // ARREGLO A MOSTRAR EN EL TABLAYOUT, UN NOMBRE POR CARDVIEW

    VersionModel(String name){
        this.name=name;
    }
}

