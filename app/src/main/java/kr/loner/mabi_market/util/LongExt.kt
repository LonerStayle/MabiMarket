package kr.loner.mabi_market.util

import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

val Long.readableTime: String
    get() {
        val now = System.currentTimeMillis()
        val diff = now - this

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "방금"
            diff < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                "${minutes}분 전"
            }
            diff < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diff)
                "$${hours}시간 전"
            }
            diff < TimeUnit.DAYS.toMillis(2) -> "하루 전"
            else -> {
                val days = TimeUnit.MILLISECONDS.toDays(diff)
                "${days}일 전"
            }
        }
    }

val Long.koreanPrice: String
    get() {

        val numberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        return "${numberFormat.format(this)}"
    }

// Long 타입의 확장 변수로 한국식 가격 표현 변환 (조 단위까지)
val Long.koreanFormattedPrice: String
    get() {
        // 천 원 미만은 그대로 숫자만 표현
        if (this < 1000) return this.koreanPrice

        // 조, 억, 만 단위 나누기
        val units = listOf("원", "만", "억", "조")
        var remainder = this
        val parts = mutableListOf<String>()

        // 조 단위로부터 천 단위까지 순서대로 계산
        for (unit in units.reversed()) {
            val divisor = when (unit) {
                "조" -> 1_000_000_000_000L
                "억" -> 100_000_000L
                "만" -> 10_000L
                else -> 1L
            }
            val value = remainder / divisor
            remainder %= divisor

            if (value > 0) {
                val formatted = if (unit == "원") {
                    String.format("%,d", value)
                } else {
                    "$value$unit"
                }
                parts.add(formatted)
            }
        }

        // 부호를 연결하여 최종 결과를 반환
        return parts.joinToString(" ")
    }