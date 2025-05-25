package com.example.demo.config

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Configuration

@Configuration
class MetricsConfig(
    private val meterRegistry: MeterRegistry
) {
    init {
        // CPU 사용량 메트릭 등록
        Gauge.builder("system.cpu.usage", Runtime.getRuntime()) { rt ->
            rt.availableProcessors().toDouble()
        }.register(meterRegistry)

        // 메모리 사용량 메트릭 등록
        Gauge.builder("system.memory.used", Runtime.getRuntime()) { rt ->
            (rt.totalMemory() - rt.freeMemory()).toDouble()
        }.register(meterRegistry)
    }
}
