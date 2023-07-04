/**
 * Класс Autopart (Автозапчасть)
 * @param brand Марка автомобиля
 * @param part Часть автомобиля
 * @param detailType Тип детали
 * @param producer Производитель
 * @param price Стоимость */
data class Autopart(
    val brand: Brand?,
    val part: Part?,
    val detailType: String,
    val producer: Producer?,
    val price: ULong
) {
    companion object{
        /**
         * build Возвращает объект Autopart
         * @return объект Autopart */
        fun build(block: AutopartBuilder.() -> Unit) = AutopartBuilder().apply(block).build()
    }

    /**
     * toString Переопределённый метод toString для класса Autopart
     * @return строковое представление объекта Autopart*/
    override fun toString(): String {
        return "Автозапчасть: \n\t " +
                "Марка автомобиля: \n\t\t Название: ${brand?.name} \n\t\t Страна: ${brand?.country} \n\t" +
                "Часть автомобиля: ${part?.toRussian()} \n\t" +
                "Тип детали: $detailType \n\t" +
                "Производитель: \n\t\t Название: ${producer?.name} \n\t\t Страна: ${producer?.country} \n\t" +
                "Стоимость: $price рублей"
    }
}

/**
 * Класс AutopartBuilder (Строитель автозапчасти)
 * @param brand Марка автомобиля
 * @param part Часть автомобиля
 * @param detailType Тип детали
 * @param producer Производитель
 * @param price Стоимость */
class AutopartBuilder{
    var brand: Brand? = null
    var part: Part? = null
    var detailType: String = ""
    var producer: Producer? = null
    var price: ULong = 0u

    /**
     * brand Устанавливает значение марки автомобиля*/
    fun brand(block: Brand.Builder.() -> Unit) {
        brand = Brand.Builder().apply(block).build()
    }

    /**
     * producer Устанавливает значение производителя*/
    fun producer(block: Producer.Builder.() -> Unit) {
        producer = Producer.Builder().apply(block).build()
    }

    /**
     * build Возвращает новый объект класса Autopart
     * @return объект Autopart*/
    fun build(): Autopart = Autopart(
        brand?: throw IllegalStateException("brand not set"),
        part?: throw IllegalStateException("no part"),
        detailType,
        producer?: throw  IllegalStateException("no producer"),
        price
    )
}

/**
 * Класс Brand (Марка автомобиля)
 * @param name Название
 * @param country Страна*/
data class Brand(
    val name: String,
    val country: String
) {
    /**
     * Вложенный класс Builder (Строитель Brand)
     * @param name Название
     * @param country Страна*/
    class Builder {
        var name: String = ""
        var country: String = ""

        /**
         * name Устанавнивает название */
        fun name(name: String) = apply { this.name = name }

        /**
         * country Устанавливает страну*/
        fun country(country: String) = apply { this.country = country }

        /**
         * build Возвращает новый объект Brand
         * @return объект Brand*/
        fun build(): Brand = Brand(name, country)
    }
}

/**
 * Класс Producer (Производитель)
 * @param name Название
 * @param country Страна*/
data class Producer(
    val name: String,
    val country: String
) {
    /**
     * Вложенный класс Builder (Строитель Producer)
     * @param name Название
     * @param country Страна*/
    class Builder {
        var name: String = ""
        var country: String = ""

        /**
         * name Устанавнивает название */
        fun name(name: String) = apply { this.name = name }

        /**
         * country Устанавливает страну*/
        fun country(country: String) = apply { this.country = country }

        /**
         * build Возвращает новый объект Brand
         * @return объект Producer*/
        fun build(): Producer = Producer(name, country)
    }
}

/**
 * Перечисление Part (Часть автомобиля)
 * @param Body Кузов
 * @param Engine Двигатель
 * @param Chassis Шасси
 * @param Transmission Трансмиссия
 * @param RunningGear Ходовая часть
 * @param BrakeSystem Тормозная система */
enum class Part{
    Body,
    Engine,
    Chassis,
    Transmission,
    RunningGear,
    BrakeSystem;

    /**
     * toRussian Расширяющий метод для перевода на русский
     * @return возвращает перевод на русский каждого типа перечисления*/
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

/**
 * Класс AutopartRepository (Репозиторий автозапчастей)
 * @param autopartRepository Список автозапчастей*/
class AutopartRepository{
    private var autopartRepository: MutableList<Autopart> = mutableListOf()

    /**
     * Add Добавляет новую автозапчасть в список*/
    fun Add(autopart: Autopart) {
        autopartRepository.add(autopart)
    }

    /**
     * AddMany Добавляет несколько автозапчастей в список*/
    fun AddMany(autopartList: MutableList<Autopart>) {
        autopartRepository.addAll(autopartList)
    }

    /**
     * Delete Удаляет автозапчасть из списка*/
    fun Delete(autopart: Autopart) {
        autopartRepository.remove(autopart)
    }

    /**
     * FindByBrand Поиск по марке автомобиля
     * @param brand Марка автомобиля
     * @return список найденных объектов*/
    fun FindByBrand(brand: String): MutableList<Autopart> {
        return autopartRepository.filter{
            it.brand?.name == brand
        }.toMutableList()
    }

    /**
     * FindByDetailType Поиск по типу детали
     * @param detailType Тип детали
     * @return список найденных объектов*/
    fun FindByDetailType(detailType: String): MutableList<Autopart> {
        return autopartRepository.filter {
            it.detailType == detailType
        }.toMutableList()
    }

    /**
     * FindByPart Поиск по части автомобиля
     * @param part Часть автомобиля
     * @return список найденных объектов*/
    fun FindByPart(part: Part): MutableList<Autopart> {
        return autopartRepository.filter {
            it.part == part
        }.toMutableList()
    }

    /**
     * Print Вывод всего списка*/
    fun Print() {
        for (item in autopartRepository) {
            println("$item\n")
        }
    }
}

/**
 * main Главная программа*/
fun main(args: Array<String>) {
    val autopartRepository = AutopartRepository()
    autopartRepository.AddMany(
        mutableListOf(
            Autopart.build {
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
            },
            Autopart.build {
                brand {
                    name = "Ламборгини"
                    country = "Италия"
                }
                part = Part.Transmission
                detailType = "Колесо"
                producer {
                    name = "Пирелли"
                    country = "Италия"
                }
                price = 20000u
            },
            Autopart.build {
                brand {
                    name = "БМВ"
                    country = "Германия"
                }
                part = Part.Body
                detailType = "Крыша"
                producer {
                    name = "Завод БМВ"
                    country = "Германия"
                }
                price = 15000u
            }
        )
    )

    //autopartRepository.Print()

    println("1: Поиск по марке автомобиля")
    val findByBrand = autopartRepository.FindByBrand("Лада")
    println("$findByBrand\n")

    println("2: Поиск по типу детали")
    val findByDetailType = autopartRepository.FindByDetailType("Крыша")
    println("$findByDetailType\n")

    println("3: Поиск по части автомобиля")
    val findByPart = autopartRepository.FindByPart(Part.Transmission)
    println("$findByPart\n")
}