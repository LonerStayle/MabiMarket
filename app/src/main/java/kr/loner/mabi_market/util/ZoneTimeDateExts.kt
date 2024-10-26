package kr.loner.mabi_market.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// 커스텀 포맷터 생성 (한글로 오후/오전 표시 및 시간, 분, 초 포함)
private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a h시 mm분 ss초", Locale.KOREAN)

val ZonedDateTime.formattedDate
    get() = this.withZoneSameInstant(ZoneId.of("Asia/Seoul")).format(formatter)

