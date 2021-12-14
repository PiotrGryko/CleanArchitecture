package piotr.example.data.db

import android.net.Uri
import androidx.room.TypeConverter
import piotr.example.domain.Constants
import java.text.SimpleDateFormat
import java.util.*


class DbConverters {
    @TypeConverter
    fun fromDate(value: Date?): String? = value?.let { SimpleDateFormat(Constants.DATE_FORMAT).format(value) }

    @TypeConverter
    fun toDate(value: String?): Date? = value?.let { SimpleDateFormat(Constants.DATE_FORMAT).parse(value) }

}