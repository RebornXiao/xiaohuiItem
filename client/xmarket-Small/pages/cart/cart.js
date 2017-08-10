
var app = getApp()
var self = null

var winAjax = {
    goodData : function(){
      var cart = app.Cart.getCart(), data = {};
      for(var key in cart){
        if(key == 'cartTotal') continue;
        data[key] = cart[key];
      }
      
      self.setData({
          goodData: data,
          cartTotal : cart.cartTotal
      })
    }
}



Page({
  data: {
    goodData : [],
    cartTotal :{
      totalNum : 0,
      totalMoney : 0.0
    },
    checkTotal :{
      totalNum : 0,
      totalMoney : 0.0
    },
    status : {checkAll : false}
  },
  onLoad: function () {
    // 初始化信息
    self = this

    // 加载购物车数据
    this.ajax.goodData();
    // 加载选中商品total
    self.setData({checkTotal : app.Cart.getCheckTotal()});

    
  },
  onCartCheckAll : function(e){
    self.setData(app.Cart.CHECKALL(e.target.dataset.check));
  },
  onCart : function(e){
    var data = {};
    data.goodData = this.data.goodData;
    data.cartTotal = this.data.cartTotal;
    data.checkTotal = this.data.checkTotal;
    self.setData(app.Cart[e.target.dataset.type](data, e.target.dataset.id))
  },
  ajax : winAjax
})