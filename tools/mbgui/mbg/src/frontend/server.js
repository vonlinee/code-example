const {Nuxt, Builder} = require('nuxt')
const app = require('express')()
const port = process.env.PORT || 3000
let HttpProxyMiddleware = require('http-proxy-middleware')

// 传入配置初始化 Nuxt.js 实例
let NuxtConfig = require('./nuxt.config.js')
const nuxt = new Nuxt(NuxtConfig)

// proxy api requests这里就是添加的proxyTable中间价的设置了
let proxyTable = NuxtConfig.proxy
Object.keys(proxyTable).forEach(function (context) {
    let options = proxyTable[context]
    if (typeof options === 'string') {
        options = {
            target: options
        }
    }
    app.use(HttpProxyMiddleware(options.filter || context, options))
})
app.use(nuxt.render) //这里是添加nuxt渲染层服务的中间件

// 在开发模式下进行编译
new Builder(nuxt).build()
    .catch((error) => {
        console.error(error)
        process.exit(1)
    })
// 监听指定端口
app.listen(port, '0.0.0.0')
console.log('>> server will be running at localhost:' + port)