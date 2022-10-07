<template>
    <view class="wrap">
        <view class="list_wrap">
            <view class="caption">数据详情</view>
            <view class="list_item" v-for="(item, index) in content.formData" :key="index">
                <view class="title_list">{{ index }}：</view>

                <view class="content_list">{{ item }}</view>
            </view>
        </view>
        <!-- 显示历史发送信息 -->
        <view class="cu-timeline" v-if="content.interviewMsg">
            <view class="cu-time" style="width: auto">历史发送信息</view>
        </view>
        <view class="cu-timeline" v-if="content.interviewMsg" v-for="(item, index) in content.interviewMsg" :key="index">
            <view class="cu-time">{{ item.sendTime }}</view>

            <view :class="'cu-item ' + (item.index + 1 == content.interviewMsg.length ? 'text-blue' : '')">
                <view class="content">
                    <view class="cu-capsule radius">
                        <view class="cu-tag bg-cyan">{{ item.time }}</view>
                        <!-- <view class="cu-tag bg-cyan">上午</view> -->
                        <!-- <view class="cu-tag line-cyan" style="margin-left: 0;">10:00</view> -->
                    </view>
                    <view class="margin-top">标题: {{ item.title }}</view>
                    <view class="margin-top">地点: {{ item.site }}</view>
                    <view class="margin-top">材料: {{ item.need }}</view>
                    <view class="margin-top">联系方式: {{ item.phonenum }}</view>
                    <view class="margin-top">备注: {{ item.remark }}</view>
                </view>
            </view>
        </view>
        <!-- 发送信息 -->
        <view>
            <!-- 发送面试信息 -->
            <view class="list_wrap" v-if="content.status == '审核中'">
                <view class="caption" style="width: 100%; text-align: center; margin-top: 20rpx">发送面试信息</view>

                <view class="margin">标题：</view>
                <input class="input_margin" style="height: 30rpx" placeholder="请输入标题（如:第一次面试）" data-type="title" type="text" @input="msgIuput" />

                <view class="margin">面试时间：</view>
                <input class="input_margin" style="height: 30rpx" placeholder="请输入日期（如:2022-5-20 13:14）" data-type="time" type="text" @input="msgIuput" />

                <view class="margin">面试地点：</view>
                <input class="input_margin" style="height: 30rpx" placeholder="请输入面试地点（如2a302）" data-type="site" type="text" @input="msgIuput" />

                <view class="margin">所需材料：</view>
                <input class="input_margin" style="height: 30rpx" placeholder="请输入所需材料（如：自我介绍）" data-type="need" type="text" @input="msgIuput" />

                <view class="margin">联系方式：</view>
                <input class="input_margin" style="height: 30rpx" placeholder="请输入电话号码" data-type="phonenum" type="text" @input="msgIuput" />

                <view class="margin">备注:</view>
                <textarea
                    class="input_margin textarea"
                    placeholder="请输入面试的其他信息(如:准备好自我介绍，携带xx......)"
                    style=""
                    data-type="remark"
                    @input="msgIuput"
                ></textarea>

                <button type="primary" style="margin-top: 20rpx" @tap="InterviewSend">是否发消息给他</button>
            </view>
            <view class="list_wrap" v-if="content.status != '审核中'">
                结果为：
                <view :style="'color: ' + (content.status == '通过' ? 'green' : 'red')">{{ content.status }}</view>
                他的面试评语：
                <textarea class="input_margin textarea" disabled>{{ content.handle_content }}</textarea>
                <button v-if="content.status == '通过'" type="primary" style="margin-top: 20rpx">已通过面试</button>
                <button v-if="content.status == '不通过'" type="primary" style="margin-top: 20rpx">不通过面试</button>
            </view>
            <view class="list_wrap" v-else>
                输入面试评语：
                <textarea class="input_margin textarea" @input="handle_contentFun" placeholder="请输入面试评语..."></textarea>
                <button type="primary" style="margin-top: 20rpx" @tap="confirm" data-status="通过">通过</button>
                <button type="warn" style="margin-top: 20rpx" @tap="confirm" data-status="不通过">不通过</button>
            </view>
        </view>
    </view>
</template>

<script>
// pages/association/freshmanDetail/freshmanDetail.js pages/association/freshmanDetail/freshmanDetail
const db = wx.cloud.database();
export default {
    data() {
        return {
            interview: {
                title: '',
                time: '',
                site: '',
                need: '',
                phonenum: '',
                remark: ''
            },

            content: {
                formData: [],
                interviewMsg: '',
                status: '',
                handle_content: ''
            },

            handle_content: ''
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        // console.log(options);
        let content = JSON.parse(options.content);
        this.setData({
            content
        });
        this.updateStatus(content._id);
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {},
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {},
    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {},
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {},
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {},
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {},
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
        //发送面试信息
        InterviewSend() {
            let interview = this.interview;
            let _id = this.content._id;
            let _openid = this.content._openid; //发送信息对象的openid

            if (interview.title == '' || interview.title == null) {
                this.showToast('标题');
            } else if (interview.time == '' || interview.time == null) {
                this.showToast('面试时间');
            } else if (interview.site == '' || interview.site == null) {
                this.showToast('面试地点');
            } else if (interview.need == '' || interview.need == null) {
                this.showToast('所需材料');
            } else if (interview.phonenum == '' || interview.phonenum == null) {
                this.showToast('联系方式');
            } else if (interview.remark == '' || interview.remark == null) {
                this.showToast('备注');
            } else {
                var date = new Date(Date.parse(new Date()));
                var Y = date.getFullYear(); //年

                var M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1; //月

                var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(); //日

                let nowTime = Y.toString() + '-' + M.toString() + '-' + D.toString();
                interview.sendTime = nowTime;
                uni.showModal({
                    title: '提示',
                    content: `确定发送面试信息`,
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                            uni.showLoading({
                                title: '发送中',
                                mask: true
                            });
                            console.log('点击确定');
                            wx.cloud
                                .callFunction({
                                    name: 'associationSend',
                                    data: {
                                        type: 9,
                                        _id: _id,
                                        interview: interview
                                    }
                                })
                                .then((res) => {
                                    console.log(res);
                                    wx.cloud
                                        .callFunction({
                                            name: 'gzh_send',
                                            data: {
                                                openid: _openid,
                                                template: {
                                                    first: interview.title,
                                                    keyword1: interview.time,
                                                    keyword2: interview.site,
                                                    keyword3: interview.need,
                                                    keyword4: interview.phonenum,
                                                    remark: interview.remark
                                                },
                                                type: '通知面试'
                                            }
                                        })
                                        .then((res) => {
                                            uni.hideLoading({
                                                success: (res) => {
                                                    uni.showToast({
                                                        title: '操作成功',
                                                        success: (res) => {
                                                            setTimeout(() => {
                                                                uni.navigateBack({
                                                                    delta: 1
                                                                });
                                                            }, 1200);
                                                        }
                                                    });
                                                }
                                            });
                                        });
                                });
                        }
                    }
                });
            }

            console.log(interview);
        },

        showToast(e) {
            uni.showToast({
                title: '请填写' + e,
                icon: 'none'
            });
        },

        msgIuput(e) {
            console.log(e);
            let type = e.currentTarget.dataset.type;
            let value = e.detail.value;
            console.log(type);
            console.log(value);

            if (type == 'title') {
                this.setData({
                    'interview.title': value
                });
            } else if (type == 'time') {
                this.setData({
                    'interview.time': value
                });
            } else if (type == 'site') {
                this.setData({
                    'interview.site': value
                });
            } else if (type == 'need') {
                this.setData({
                    'interview.need': value
                });
            } else if (type == 'phonenum') {
                this.setData({
                    'interview.phonenum': value
                });
            } else if (type == 'remark') {
                this.setData({
                    'interview.remark': value
                });
            }
        },

        // 标记状态
        updateStatus(_id) {
            db.collection('assoFreshApply')
                .where({
                    _id
                })
                .update({
                    data: {
                        read: true
                    }
                });
        },

        // 复制
        copy() {},

        handle_contentFun(e) {
            this.setData({
                handle_content: e.detail.value
            });
        },

        getStatusMsg(status) {
            let interview = this.interview;
            let name = this.content.content.association[0].detail;
            var date = new Date(Date.parse(new Date()));
            var Y = date.getFullYear(); //年

            var M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1; //月

            var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(); //日

            let nowTime = Y.toString() + '-' + M.toString() + '-' + D.toString();

            if (status == '通过') {
                interview.title = '恭喜你通过' + name + '的面试！！！';
                interview.phonenum = this.content.content.association[1].detail;
                interview.remark = this.handle_content;
                interview.need = '恭喜你已通过面试';
                interview.site = '恭喜你已通过面试';
                interview.time = '恭喜你已通过面试';
                interview.sendTime = nowTime;
                return interview;
            } else if (status == '不通过') {
                interview.title = '很遗憾你没有通过' + name + '的面试';
                interview.phonenum = this.content.content.association[1].detail;
                interview.remark = this.handle_content;
                interview.need = '无';
                interview.site = '无';
                interview.time = '无';
                interview.sendTime = nowTime;
                return interview;
            }
        },

        confirm(e) {
            if (!this.handle_content || this.handle_content == '') {
                this.handle_content = '管理员没有置评';
            }

            let status = e.currentTarget.dataset.status;
            let interview = this.getStatusMsg(status);
            let _openid = this.content._openid;
            uni.showModal({
                title: '提示',
                content: `确定${status}该同学请求`,
                showCancel: true,
                cancelText: '取消',
                cancelColor: '#000000',
                confirmText: '确定',
                confirmColor: '#3CC51F',
                success: (result) => {
                    if (result.confirm) {
                        uni.showLoading({
                            title: '操作中',
                            success: (res) => {
                                wx.cloud
                                    .callFunction({
                                        name: 'association',
                                        data: {
                                            action: 'changeApply',
                                            data: {
                                                id: this.content._id,
                                                handle_content: this.handle_content,
                                                status,
                                                interview
                                            }
                                        }
                                    })
                                    .then((res) => {
                                        wx.cloud
                                            .callFunction({
                                                name: 'gzh_send',
                                                data: {
                                                    openid: _openid,
                                                    template: {
                                                        first: interview.title,
                                                        keyword1: interview.time,
                                                        keyword2: interview.site,
                                                        keyword3: interview.need,
                                                        keyword4: interview.phonenum,
                                                        remark: interview.remark
                                                    },
                                                    type: '通知面试'
                                                }
                                            })
                                            .then((res) => {
                                                uni.hideLoading({
                                                    success: (res) => {
                                                        uni.showToast({
                                                            title: '操作成功',
                                                            success: (res) => {
                                                                setTimeout(() => {
                                                                    uni.navigateBack({
                                                                        delta: 1
                                                                    });
                                                                }, 1200);
                                                            }
                                                        });
                                                    }
                                                });
                                            });
                                    });
                            }
                        });
                    }
                }
            });
        }
    }
};
</script>
<style>
@import './freshmanDetail.css';
</style>
