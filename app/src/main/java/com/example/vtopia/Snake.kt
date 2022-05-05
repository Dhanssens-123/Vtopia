package com.example.vtopia

class Snake(cockpit: AirPlane) {
    var chain = mutableListOf<Aerial>(cockpit)
    var xCockpit = mutableListOf<Float>()
    var yCockpit = mutableListOf<Float>()
    var xChain = mutableListOf<Float>()
    var yChain = mutableListOf<Float>()

    fun ellongate(cloud: Cloud, cockpit: AirPlane) {
        updatePos(cockpit)
        xChain.add(xCockpit[10*chain.size])
        yChain.add(yCockpit[10*chain.size])
        cloud.r.offsetTo(xChain.last(),yChain.last())
        chain.add(cloud)
    }

    fun update(clouds : ArrayList<Cloud>, cockpit: AirPlane) {
        slide(xCockpit, cockpit.r.left)
        slide(yCockpit, cockpit.r.top)
        if (chain.size > 1) {
            for (i in 0 until xChain.size) xChain[i] = xCockpit[10 * (i + 1)]
            for (i in 0 until yChain.size) yChain[i] = yCockpit[10 * (i + 1)]
            for (i in 1 until chain.size) chain[i].r.offsetTo(xChain[i-1], yChain[i-1])
        }
    }

    fun updatePos(cockpit: AirPlane) {
        for (i in 1..10) {
            xCockpit.add(chain.last().r.left)
            yCockpit.add(chain.last().r.top)
        }
    }

    fun slide(lst : MutableList<Float>, x : Float) {
        val copylst = lst.toTypedArray()
        for (i in 1 until lst.size) lst[i] = copylst[i-1]
        lst[0] = x
    }
}