package rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures

data class ExtendedBarberWithAverageGrade(
    var id: Int,
    var email: String,
    var password: String,
    var barbershopName: String,
    var price: Double,
    var phone: String,
    var country: String,
    var city: String,
    var municipality: String,
    var address: String,
    var workingDays: String,
    var workingHours: String,

    var averageGrade: Float
)
