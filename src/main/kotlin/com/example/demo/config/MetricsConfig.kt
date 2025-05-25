package com.example.demo.config

import org.springframework.context.annotation.Configuration

@Configuration
class MetricsConfig(
//    private val meterRegistry: MeterRegistry
) {
    init {
//        Gauge.builder("system.cpu.usage", Runtime.getRuntime()) { rt ->
//            rt.availableProcessors().toDouble()
//        }.register(meterRegistry)
//        Gauge.builder("system.memory.used", Runtime.getRuntime()) { rt ->
//            (rt.totalMemory() - rt.freeMemory()).toDouble()
//        }.register(meterRegistry)
    }
}
