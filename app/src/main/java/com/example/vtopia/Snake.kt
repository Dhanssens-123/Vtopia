package com.example.vtopia

class Snake(cockpit: AirPlane) {

    private var chain = mutableListOf<Aerial>(cockpit)
    private var xCockpit = mutableListOf<Float>()
    private var yCockpit = mutableListOf<Float>()
    private var xChain = mutableListOf<Float>()
    private var yChain = mutableListOf<Float>()
    private var STEP = 20

    init {
        this.updatePos()
    }

    fun ellongate(cloud: Cloud) {
        updatePos()
        xChain.add(xCockpit[STEP*chain.size])
        yChain.add(yCockpit[STEP*chain.size])
        cloud.getRect().offsetTo(xChain.last(),yChain.last())
        chain.add(cloud)
    }

    fun update(cockpit: AirPlane) {
        slide(xCockpit, cockpit.getRect().left)
        slide(yCockpit, cockpit.getRect().top)
        if (chain.size > 1) {
            for (i in 0 until xChain.size) xChain[i] = xCockpit[STEP * (i + 1)]
            for (i in 0 until yChain.size) yChain[i] = yCockpit[STEP * (i + 1)]
            for (i in 1 until chain.size) chain[i].getRect().offsetTo(xChain[i-1],yChain[i-1])
        }
    }

    fun updatePos() {
        for (i in 1..STEP) {
            xCockpit.add(chain.last().getRect().left)
            yCockpit.add(chain.last().getRect().top)
        }
    }

    fun slide(lst : MutableList<Float>, x : Float) {
        val copylst = lst.toTypedArray()
        for (i in 1 until lst.size) lst[i] = copylst[i-1]
        lst[0] = x
    }

    fun getSnake() : MutableList<Aerial> {
        return chain
    }
}