data class Autopart(
    val brand: Brand?,
    val part: Part?,
    val detailType: String,
    val producer: Producer?,
    val price: ULong
) {
    companion object{
        fun build(block: AutopartBuilder.() -> Unit) = AutopartBuilder().apply(block).build()
    }

    override fun toString(): String {
        return "Автозапчасть: \n\t " +
                "Марка автомобиля: \n\t\t Название: ${brand?.name} \n\t\t Страна: ${brand?.country} \n\t" +
                "Часть автомобиля: ${part?.toRussian()} \n\t" +
                "Тип детали: $detailType \n\t" +
                "Производитель: \n\t\t Название: ${producer?.name} \n\t\t Страна: ${producer?.country} \n\t" +
                "Стоимость: $price рублей"
    }
}

class AutopartBuilder{
    var brand: Brand? = null
    var part: Part? = null
    var detailType: String = ""
    var producer: Producer? = null
    var price: ULong = 0u

    fun brand(block: Brand.Builder.() -> Unit) {
        brand = Brand.Builder().apply(block).build()
    }
    fun producer(block: Producer.Builder.() -> Unit) {
        producer = Producer.Builder().apply(block).build()
    }

    fun build(): Autopart = Autopart(
        brand?: throw IllegalStateException("brand not set"),
        part?: throw IllegalStateException("no part"),
        detailType,
        producer?: throw  IllegalStateException("no producer"),
        price
    )
}

data class Brand(
    val name: String,
    val country: String
) {
    class Builder {
        var name: String = ""
        var country: String = ""

        fun name(name: String) = apply { this.name = name }
        fun country(country: String) = apply { this.country = country }
        fun build(): Brand = Brand(name, country)
    }
}

data class Producer(
    val name: String,
    val country: String
) {
    class Builder {
        var name: String = ""
        var country: String = ""

        fun name(name: String) = apply { this.name = name }
        fun country(country: String) = apply { this.country = country }
        fun build(): Producer = Producer(name, country)
    }
}

enum class Part{
    Body,
    Engine,
    Chassis,
    Transmission,
    RunningGear,
    BrakeSystem;

    fun toRussian(): String {
        return when (this) {
            Body -> "Кузов"
            Engine -> "Двигатель"
            Chassis -> "Шасси"
            Transmission -> "Трансмиссия"
            RunningGear -> "Ходовая часть"
            BrakeSystem -> "Тормозная система"
        }
    }
}

fun main(args: Array<String>) {
    val autopart = Autopart.build {
        brand {
            name = "Лада"
            country = "Россия"
        }
        part = Part.Engine
        detailType = "Поршень"
        producer {
            name = "Тольятинский Завод 'АвтоВАЗ'"
            country = "Россия"
        }
        price = 30000u
    }

    println(autopart.toString())
}