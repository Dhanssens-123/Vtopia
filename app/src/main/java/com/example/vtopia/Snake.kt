package com.example.vtopia

class Snake(cockpit: AirPlane) {
    var chain = mutableListOf<Aerial>(cockpit)
    var posx = mutableListOf<Float>(cockpit.r.left)
    var posy = mutableListOf<Float>(cockpit.r.top)

    fun ellongate(cloud: Cloud, cockpit: AirPlane, lesParois: Array<Parois>) {
        posx.add(chain.last().r.left - chain.last().dx*75f)
        posy.add(chain.last().r.top - chain.last().dy*75f)
        cloud.dx = chain.last().dx
        cloud.dy = chain.last().dy
        cloud.r.offsetTo(posx.last(),posy.last())
        chain.add(cloud)
    }
}