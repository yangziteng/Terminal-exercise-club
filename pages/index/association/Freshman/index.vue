<!-- 社团活动->报名 -->
<template>
    <view>
        <view class="wrap">
            <swiper
                :indicator-dots="true"
            
                :style="'height: ' + (content.ShowHeight > 300 ? 370 + 'rpx' : content.ShowHeight + 'px') + ';'"
            >
                <block class="photo" v-for="(item, index) in content.AllPhoto" :key="item.Photo">
                    <swiper-item>
                        <image :src="item" mode="aspectFill" style="z-index: 11" @tap="ShowImg" class="ShowImg" :data-index="index" />
                    </swiper-item>
                </block>
            </swiper>

            <view style="width: 100vw; display: flex; align-items: center; justify-content: center; font-size: 25rpx; margin-top: 15rpx">点击图片查看完整海报</view>
            <view style="width: 100vw" @tap="ShowImgB">
                <view
                    style="
                        margin: 0 auto;
                        width: 93vw;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 25rpx;
                        margin-top: 25rpx;
                        background-color: white;
                        height: 60rpx;
                        border-radius: 15rpx;
                    "
                >
                    点击我查看咨询群二维码
                </view>
            </view>

            <view class="mess_wrap">
                <view class="mess_caption">社团信息</view>
                <view class="mess_list">
                    <view class="mess_item" v-for="(item, index) in content.association" :key="index">
                        <view class="title">{{ item.name }}</view>

                        <view class="content">{{ item.detail }}</view>
                    </view>
                </view>
                <view class="intro">招新标题</view>
                <view class="mess_list_">{{ content.Title }}</view>
            </view>
            <form @submit="parseEventDynamicCode($event, apply == false ? (timeOut == true ? 'submit' : 'timeOut') : '')">
                <view class="apply" v-if="content.question.length != 0">
                    <view class="apply_caption">报名申请表</view>
                    <view class="input_list">
                        <view class="input_item" v-for="(item, index) in content.question" :key="index">
                            <view class="input_title">{{ item.content }}</view>

                            <input placeholder="请输入" class="input_content" :name="item.content" />
                        </view>
                    </view>
                </view>
                <view class="apply">
                    <view class="apply_caption">选择意向部门</view>
                    <view class="input_list">
                        <view class="input_item2">
                            <checkbox-group @change="checkboxChange">
                                <block v-for="(item, index) in hobby" :key="item.value">
                                    <checkbox style="padding: 20rpx" :value="item.value">{{ item.name }}</checkbox>
                                </block>
                            </checkbox-group>
                        </view>
                    </view>
                </view>
                <view class="endTime">截止时间:{{ content.endTime }}</view>
                <button
                    class="end"
                    form-type="submit"
                    :style="'background-color:' + (apply == false ? (timeOut == true ? '' : '#777777') : '#777777')"
                    @tap="parseEventDynamicCode($event, apply == true ? 'nosubmit' : '')"
                >
                    {{ apply == false ? (timeOut == true ? '完成提交' : '已截止') : '已报名' }}
                </button>
            </form>
        </view>

        <!-- 二维码弹窗 -->

        <view :class="'cu-modal ' + (modalName == 'Image' ? 'show' : '')">
            <view class="cu-dialog">
                <image class="bg-img" src="https://s1.ax1x.com/2022/09/16/vzW7ss.jpg" show-menu-by-longpress="1" style="height: 730rpx; width: 660rpx" mode="aspectFill">
                    <view class="cu-bar justify-end text-white">
                        <view class="action" @tap="hideModalQR">
                            <text class="cuIcon-close"></text>
                        </view>
                    </view>
                </image>

                <view class="cu-bar bg-white">
                    <view class="action margin-0 flex-sub solid-left" @tap="hideModalQR">长按关注公众号获取后续面试信息，点我关闭</view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
// pages/more/pages/Freshman/Freshman.js
const db = wx.cloud.database();
let username = '';

var time = require('../../../../utils/time.js');

export default {
    data() {
        return {
            apply: false,

            //是否已经报名,
            timeOut: false,

            //是否截止
            hobby: [],

            //检查是否关注公众号了，false关注了,true没关注，控制公众号弹窗
            judgeSend: false,

            intro: '',
            department: '',

            content: {
                ShowHeight: 0,
                AllPhoto: [],
                association: [],
                Title: '',
                question: [],
                endTime: ''
            },

            modalName: ''
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        if (options.fromQrCode) {
            db.collection('Campus-Circle')
                .where({
                    _id: options._id
                })
                .get()
                .then((res) => {
                    let form = res.data[0];
                    let data = {
                        AllPhoto: [form.imgUrl],
                        Cover: form.imgUrl,
                        CoverHeight: form.CoverHeight,
                        CoverWidth: form.CoverWidth,
                        Label: '社团招新',
                        School: form.school,
                        ShowHeight: form.ShowHeight,
                        Time: Date.now(),
                        question: form.freshman,
                        association: form.assoMess,
                        index: form.count + '社团',
                        //??
                        endTime: form.date,
                        personArr: form.personArr,
                        //??
                        _id: form._id,
                        cloud: [] //??
                    };
                    this.loadData(res.data[0]);
                });
            console.log('进入1');
        } else if (options.content) {
            let content = JSON.parse(options.content);
            this.loadData(content);
        }
    },
    // 查询是否已参与并操作
    // getPerson(){
    // },

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
        ShowImgB() {
            var Photo = this.content.erweimaUrl;
            console.log(Photo);
            uni.previewImage({
                current: Photo,
                urls: [Photo]
            });
        },

        loadData(content) {
            console.log(content);
            var department = content.association[5].detail;
            department = department.trim().split(' ');
            department.forEach((e) =>
                this.hobby.push({
                    value: e,
                    name: e
                })
            );
            this.setData({
                intro: content.association[4].detail,
                department: department,
                hobby: this.hobby
            });
            var date = new Date(Date.parse(new Date()));
            var Y = date.getFullYear(); //年

            var M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1; //月

            var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(); //日

            let nowTime = Y.toString() + '-' + M.toString() + '-' + D.toString();

            if (content.endTime > nowTime) {
                this.setData({
                    timeOut: true
                });
            }

            let _id = content._id;
            this.setData({
                content
            });
            uni.showLoading({
                title: '加载中',
                mask: true
            });
            wx.cloud
                .callFunction({
                    name: 'associationSend',
                    data: {
                        _id,
                        type: 4
                    }
                })
                .then((res) => {
                    // console.log(res);
                    this.getApplyStatus();
                });
        },

        // 查询用户是否报名
        getApplyStatus() {
            let index = this.content.association[3].detail;
            let Title = this.content.Title;
            console.log(Title);
            wx.cloud
                .callFunction({
                    name: 'association',
                    data: {
                        action: 'checkApply',
                        data: {
                            index,
                            Title
                        }
                    }
                })
                .then((res) => {
                    console.log(res);
                    this.setData({
                        apply: res.result.data
                    });
                    uni.hideLoading({
                        success: (res) => {}
                    });
                });
        },

        checkboxChange(e) {
            const hobby = this.hobby;
            const values = e.detail.value;

            for (let i = 0; i < hobby.length; i++) {
                hobby[i].checked = false;

                for (let j = 0; j < values.length; j++) {
                    if (hobby[i].value === values[j]) {
                        hobby[i].checked = true;
                        break;
                    }
                }
            } //遍历里面数据

            this.hobby.forEach((item) => {
                console.log(item);
            }); //setData（）函数用于将逻辑层数据发送到视图层，同时对应的改变this.data的值。

            this.setData({
                hobby
            });
        },

        // 提交报名表单
        deal_with_formData(formData) {
            let str = '';
            Object.keys(formData).forEach((val, index) => {
                console.log(val + ':' + index + ',' + formData[val]);

                if (index > 2 || index == 2) {
                    str = str + String(val) + ':' + '\n' + String(formData[val]) + ';' + '\n';
                }
            });
            return str;
        },

        // 提交报名表单
        // {
        //   "openid":""
        //     "template": {
        //       "first":"已有学生报名",
        //       "keyword1":"社团招新",  //报名项目
        //       "keyword2":"杨子腾", //姓名
        //       "keyword3":"15813194056", //联系方式
        //       "keyword4":"数学建模协会", //企业名称
        //       "keyword5":"2022-9-10",
        //       "remark":"点击查看更多消息"
        //     },
        //     "type":"通知组织"
        //   }
        submit(e) {
            let formData = e.detail.value;
            let index = this.content.association[3].detail; //社团管理者学号

            let _openid = this.content._openid;
            let sendTime = this.gettime();
            let question = this.deal_with_formData(formData);
            console.log(formData);
            var formData_arr = Object.keys(formData);

            for (var i = 0; i < formData_arr.length; i++) {
                if (formData[formData_arr[i]] == '') {
                    uni.showToast({
                        title: '您还未填写' + formData_arr[i],
                        icon: 'none'
                    });
                    return -1;
                }
            }

            let department = [];
            this.hobby.forEach((e) => {
                if (e.checked) {
                    department.push(e.value + ' ');
                }
            });
            formData['选择的部门'] = department; // Object.keys(formData).forEach((e)=>{
            //   console.log(formData[e])
            //   if(formData[e]==""){
            //     wx.showToast({
            //       title: '您还未填写'+e,
            //       icon:"none"
            //     })
            //     console.log('您还未填写'+e)
            //   }
            // })

            let Title = this.content.Title;
            uni.showLoading({
                title: '报名中',
                mask: true,
                success: (result) => {
                    db.collection('assoFreshApply')
                        .add({
                            data: {
                                username: formData['学号'],
                                formData,
                                index,
                                read: false,
                                status: '审核中',
                                time: time.formatTime(new Date()),
                                content: this.content,
                                Title
                            }
                        })
                        .then((res) => {
                            wx.cloud
                                .callFunction({
                                    name: 'gzh_send',
                                    data: {
                                        openid: _openid,
                                        template: {
                                            first: '已有学生报名',
                                            keyword1: this.content.Label,
                                            //报名项目
                                            keyword2: formData['姓名'],
                                            //姓名
                                            keyword3: formData['联系方式'],
                                            //联系方式
                                            keyword4: this.content.association[0].detail,
                                            //企业名称
                                            keyword5: sendTime,
                                            //日期
                                            remark: question
                                        },
                                        type: '通知组织'
                                    }
                                })
                                .then(async (res) => {
                                    let result = await wx.cloud.callFunction({
                                        name: 'associationSend',
                                        data: {
                                            type: 11
                                        }
                                    });
                                    let openid = result.result;
                                    let ques = question + '\n' + '请耐心等待面试通知';
                                    let judge = await wx.cloud.callFunction({
                                        name: 'gzh_send',
                                        data: {
                                            openid: openid,
                                            template: {
                                                first: '您已成功报名' + this.content.association[0].detail + '的活动',
                                                keyword1: this.content.Label,
                                                //报名项目
                                                keyword2: formData['姓名'],
                                                //姓名
                                                keyword3: formData['联系方式'],
                                                //联系方式
                                                keyword4: this.content.association[0].detail,
                                                //企业名称
                                                keyword5: sendTime,
                                                //日期
                                                remark: ques
                                            },
                                            type: '通知组织'
                                        }
                                    });
                                    console.log(judge.result.code);

                                    if (judge.result.code == '10000') {
                                        //未关注公众号
                                        uni.hideLoading();
                                        await uni.showToast({
                                            title: '请关注公众号，获取面试信息提醒',
                                            icon: 'none',
                                            duration: 1500,
                                            success: (result) => {
                                                this.setData({
                                                    apply: true
                                                });
                                            }
                                        });
                                        this.setData({
                                            modalName: 'Image'
                                        }); // this.setData({
                                        //   judgeSend:true
                                        // })
                                    } else if (judge.result.code == '200') {
                                        //已关注公众号
                                        uni.hideLoading();
                                        uni.showToast({
                                            title: '报名成功已发送给此社团',
                                            icon: 'none',
                                            image: '',
                                            duration: 1500,
                                            mask: false,
                                            success: (result) => {
                                                this.setData({
                                                    apply: true
                                                });
                                            }
                                        });
                                    }
                                })
                                .catch((e) => {
                                    uni.showToast({
                                        title: '你还未关注公众号',
                                        icon: 'none'
                                    });
                                });
                        });
                    uni.hideLoading();
                    uni.showToast({
                        title: '报名成功',
                        icon: 'loading',
                        image: '',
                        duration: 1800,
                        mask: false,
                        success: (result) => {
                            this.setData({
                                apply: true
                            });
                        }
                    });
                }
            });
        },

        hideModalQR() {
            this.setData({
                modalName: null
            });
        },

        gettime() {
            var date = new Date(Date.parse(new Date()));
            var Y = date.getFullYear(); //年

            var M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1; //月

            var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(); //日

            let nowTime = Y.toString() + '-' + M.toString() + '-' + D.toString();
            return nowTime;
        },

        // timeOut
        timeOutFun() {
            uni.showToast({
                title: '报名已截止',
                icon: 'none',
                image: '',
                duration: 1500,
                mask: false,
                success: (result) => {},
                fail: () => {},
                complete: () => {}
            });
        },

        // nosubm
        nosubmit() {
            uni.showToast({
                title: '你已报名',
                icon: 'none',
                image: '',
                duration: 1500,
                mask: false,
                success: (result) => {}
            });
        },

        ShowImg: function (e) {
            var Photo = this.content.AllPhoto;
            var index = e.target.dataset.index;
            uni.previewImage({
                current: Photo[index],
                urls: Photo
            });
        }
    }
};
</script>
<style>
@import './Freshman.css';
</style>
