
var app = getApp();
var self = null;

var winAjax = {

}


Page({
  data: {
    status : {
      tabActive : 0
    }
  },
  onLoad: function () {
    // 初始化信息
    self = this

  },
  onTab : function(e){
    self.setData({
      status: {
        tabActive : e.target.dataset.tabactive
      }
    })
  },
  onOrderInfo : function(e){
    wx.navigateTo({
      url: 'orderInfo?id='+e.target.dataset.id
    })
  },
  ajax : winAjax
})