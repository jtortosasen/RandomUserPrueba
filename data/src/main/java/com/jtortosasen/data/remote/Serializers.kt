package com.jtortosasen.data.remote

import co.touchlab.kermit.Logger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object UserFullNameSerializer : JsonTransformingSerializer<String>(String.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val first = element.jsonObject.lGet("first")?.jsonPrimitive?.content
        val last = element.jsonObject.lGet("last")?.jsonPrimitive?.content
        return JsonPrimitive("$first $last")
    }
}

object UserPictureSerializer : JsonTransformingSerializer<String>(String.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        JsonPrimitive(element.jsonObject.lGet("large")?.jsonPrimitive?.content)
}

object UserLocationSerializer : JsonTransformingSerializer<Pair<Double, Double>>(PairSerializer(Double.serializer(), Double.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val latitude = element.jsonObject.lGet("coordinates")?.jsonObject?.lGet("latitude")?.jsonPrimitive?.content?.toDouble()
        val longitude = element.jsonObject.lGet("coordinates")?.jsonObject?.lGet("longitude")?.jsonPrimitive?.content?.toDouble()
        return buildJsonObject {
            put("first", JsonPrimitive(latitude))
            put("second", JsonPrimitive(longitude))
        }
    }
}

object UserRegisteredDateSerializer : KSerializer<LocalDateTime> {
    private val dateFormat = DateTimeFormatter.ISO_DATE_TIME

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UserRegisteredDate", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDateTime) = encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): LocalDateTime {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val dateStr = element.lGet("date")?.jsonPrimitive?.content
        val instant = Instant.parse(dateStr) // Parsea como Instant
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}

fun JsonElement.lGet(key: String): JsonElement? {
    return (this as? JsonObject)?.get(key) ?: run {
        Logger.w { "Key \"$key\" not found" }
        null
    }
}