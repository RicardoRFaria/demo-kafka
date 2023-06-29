package com.ricardofaria.demokafka.controller

import com.ricardofaria.demokafka.model.PriceMessage
import com.ricardofaria.demokafka.service.PriceService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/price")
class PriceController(private val priceService: PriceService) {

    @RequestMapping(
        value = ["/"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun post(@RequestBody priceMessage: PriceMessage): Map<String, String> {
        priceService.sendPrice(priceMessage)
        return mapOf("result" to "ok")
    }

}