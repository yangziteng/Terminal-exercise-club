<!-- 社团列表  -->
<template>
    <view>
        <view class="classify" v-for="(a, index) in levels" :key="index">
            <view style="margin-left: 41rpx; height: 80rpx; width: 50%; text-align: left; line-height: 80rpx; font-size: 28rpx; font-weight: 550" v-if="index != 1">
                <view :style="'height:' + (index != 1 ? 80 : 0) + 'rpx; width: 50%;text-align: left;line-height: 80rpx;font-size: 28rpx;font-weight: 550;'" v-if="index != 1">
                    {{ a.level_name }}
                </view>
            </view>

            <view class="dakatop_box2_input_left" v-if="(a.level_name == '院级') + ' '">
                <!-- <picker range="{{acadenmic_club_list}}" name="startTime" value="{{acadenmic}}" bindchange="bindstattimeChange">
            <view class="picker" placeholder="请选择所属学院">{{acadenmic}}</view>
        </picker> -->
            </view>

            <scroll-view
                v-if="a.departments_list2.length != 0"
                class="departments2"
                :scroll_x="true"
                :id="a.index"
                :scroll_left="scrolllocal2[index]"
                style="display: flex; white-space: nowrap"
            >
                <view class="department2" :id="a.index + ',' + index" style="display: inline-block" @tap="choose2" v-for="(item, index1) in a.departments_list2" :key="index1">
                    <view class="top_pic2">
                        <image mode="aspectFill" :src="item.imageurl"></image>
                    </view>

                    <view class="department_info">
                        <view class="department_name">{{ item.roomname }}</view>
                        <text class="department_introduce" :disabled="true">{{ item.introduce }}</text>
                    </view>
                </view>
            </scroll-view>

            <!-- <scroll-view wx:if="{{a.departments_list2.length == 0}}" class="departments2" scroll_x="true" id="{{a.index}}" scroll_left="{{scrolllocal2[index]}}" style=" display: flex;white-space: nowrap;">
        <view class="department2" wx:key="index" style="display: inline-block;" bindtap="choose2">
            <view class="top_pic2">
                <image mode="aspectFill" src="https://636c-cloud1-6gtqj1v4873bad50-1307814679.tcb.qcloud.la/WeSchool.jpg?sign=8a7913dcfd92e670836e52d1427f84f6&t=1662830033" lazy-load="1"></image>
            </view>
            <view class="department_info">
                <view class="department_name">空白卡片</view>
                <text class="department_introduce" disabled="true">请重新选择学院</text>
            </view>
        </view>
    </scroll-view> -->
        </view>

        <view @tap="toCreate_Club" class="new_club">创建社团</view>

        <view class="head_title">校级机构</view>

        <view class="departments">
            <view class="department" :id="index" @tap="choose" v-for="(item, index) in department_list" :key="index">
                <view class="top_pic">
                    <image class="schoolImage" mode="aspectFill" :src="item.imageurl" :lazy-load="true" />
                </view>

                <view class="txt_content">
                    <view class="txt_name_left">
                        <view>{{ item.roomname }}</view>
                    </view>
                    <view class="btn_moreInfo_right">
                        <view class="btn_more">详情</view>
                    </view>
                </view>
            </view>
        </view>
        <view class="head_title">实验室(工作室)</view>

        <view class="departments">
            <view class="department" data-name="实验室(工作室)" :id="index" @tap="choose3" v-for="(item, index) in top" :key="index">
                <view class="top_pic">
                    <image class="schoolImage" mode="aspectFill" :src="item.imageurl" :lazy-load="true" />
                </view>

                <view class="txt_content">
                    <view class="txt_name_left">
                        <view>{{ item.roomname }}</view>
                    </view>
                    <view class="btn_moreInfo_right">
                        <view class="btn_more">详情</view>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
let db = wx.cloud.database();
const _ = db.command;
export default {
    data() {
        return {
            acadenmic: '请选择所属学院',

            //这个用户输入的开始时间，下面那个是结束时间
            //获取屏幕可用宽度
            screenWidth: uni.getSystemInfoSync().screenWidth,

            center_height: uni.getSystemInfoSync().windowHeight * 0.5,

            //屏幕中点
            center_width: uni.getSystemInfoSync().screenWidth * 0.5,

            //屏幕中点
            welcome_txt: '',

            //动画
            welcome_txt2: '',

            //动画
            welcome_txt3: '',

            //动画
            ani_show_list: [],

            //动画
            ani_show_list2: [],

            //动画
            ani_show_list3: [],

            //动画
            club: {},

            scrolllocal2: [],

            //滑动位置存储2
            weschool: [],

            department_list: [],

            //校级
            levels: [],

            //其他
            acadenmic_club_list: [
                '石油工程学院',
                '化学工程学院',
                '化学学院',
                '材料科学与工程学院',
                '环境科学与工程学院',
                '生物与食品工程学院',
                '机电工程学院',
                '自动化学院',
                '电子信息工程学院',
                '理学院',
                '经济管理学院',
                '文法学院',
                '外国语学院',
                '体育学院',
                '艺术与设计学院',
                '马克思主义学院',
                '计算机学院',
                '建筑工程学院'
            ],

            memory_list: [],
            content: [1, 2, 3, 4, 5],
            pxopen: false,
            pxshow: false,
            active: true,
            top: '',
            scrolllocal: '',

            a: {
                level_name: '',
                departments_list2: [],
                index: ''
            }
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function () {
        var that = this;
        uni.showLoading({
            title: '数据加载中请稍后~'
        });
        let school = uni.getStorageSync('args').school;
        wx.cloud
            .callFunction({
                name: 'associationSend',
                data: {
                    type: 10,
                    status: true,
                    school: school
                }
            })
            .then((res) => {
                console.log(res, 233);
                uni.showToast({
                    title: '加载成功',
                    icon: 'none'
                });
                var data = res.result.data;
                var club_List = [];
                data.forEach((element) => {
                    var club = {
                        association_id: '',
                        logo: element.logoUrl,
                        erweima: element.erweimaUrl || '',
                        //兼容
                        association_name: element.hostMess.association,
                        desc: element.hostMess.detail,
                        type: element.school_name,
                        academic: element.hostMess.school_name,
                        xueyuan: element.xueyuan || ''
                    };
                    club_List.push(club);
                });
                console.log(club_List, 222222222);
                that.DataFormat(club_List);
                uni.setStorage({
                    key: 'clubs',
                    data: club_List
                });
                uni.getStorage({
                    key: 'academic',

                    success(res) {
                        that.setData({
                            acadenmic: res.data
                        });
                        that.select_academic_clubs(res.data);
                    }
                });
            });
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {},
    methods: {
        // getData() {
        //   var that = this
        //   wx.getStorage({
        //     key: 'clubs',
        //     success(res) {
        //       that.DataFormat(res.data)
        //     }
        //   })
        // },
        DataFormat(res) {
            var that = this;
            var data = res;
            var ii = 0;
            var array1 = [];
            var array2 = [];
            var array3 = [];
            var top = [];
            var department_list = [];
            var levels = [
                {
                    level_name: '社团',
                    index: '0',
                    departments_list2: []
                },
                {
                    level_name: '社团2',
                    index: '1',
                    departments_list2: []
                },
                {
                    level_name: '院级',
                    index: '2',
                    departments_list2: []
                }
            ];
            var len = data.length;

            for (var i = 0; i < len; i++) {
                var club = {
                    id: data[i].association_id,
                    imageurl: data[i].logo,
                    erweimaUrl: data[i].erweima || '',
                    roomname: data[i].association_name,
                    introduce: data[i].desc,
                    academic: data[i].academic,
                    xueyuan: data[i].xueyuan || ''
                }; // if (data[i].type == "大佬") {
                //   top.push(club)
                // }

                if (data[i].type == '实验室(工作室)') {
                    top.push(club);
                }

                if (data[i].type == '校级') {
                    department_list.push(club);
                }

                if (data[i].type == '院级') {
                    array1.push(club);
                }

                if (data[i].type == '社团') {
                    if (ii % 2 == 0) {
                        array2.push(club);
                    }

                    if (ii % 2 == 1) {
                        array3.push(club);
                    }

                    ii++;
                }
            }

            levels[0].departments_list2 = array2;
            levels[1].departments_list2 = array3;
            levels[2].departments_list2 = array1;
            that.setData({
                department_list: department_list,
                top,
                memory_list: array1,
                levels: levels
            });
            console.log('top', top);
            console.log('this.data.orders', this.orders);
        },

        choose: function (e) {
            var that = this;
            var index = e.currentTarget.id;
            console.log(e);
            var club = that.department_list[index];
            var len = index * 200;
            uni.setStorage({
                key: 'club',
                data: club
            });
            that.setData({
                scrolllocal: len
            });
            uni.navigateTo({
                url: '/pages/HOT/HotTop/HotTop?content=社团详情'
            });
        },

        choose2: function (e) {
            var that = this;
            var id = e.currentTarget.id;
            var club = that.levels[id[0]].departments_list2[id[2]];
            var list = that.scrolllocal2;
            list[id[0]] = id[2] * 250;
            uni.setStorageSync('club', club);
            that.setData({
                scrolllocal2: list
            });
            uni.navigateTo({
                url: '/pages/HOT/HotTop/HotTop?content=社团详情'
            });
        },

        choose3(e) {
            var that = this;
            var index = e.currentTarget.id;
            console.log(e);
            var club = that.top[index];
            var len = index * 200;
            uni.setStorage({
                key: 'club',
                data: club
            });
            that.setData({
                scrolllocal: len
            });
            uni.navigateTo({
                url: '/pages/HOT/HotTop/HotTop?content=社团详情'
            });
        },

        bindstattimeChange: function (e) {
            var academicname = this.acadenmic_club_list[e.detail.value];
            console.log(academicname);
            this.select_academic_clubs(academicname);
            uni.setStorageSync('academic', academicname);
        },

        select_academic_clubs: function (club_name) {
            var levels = this.levels;
            var list = this.memory_list;
            var club_list = [];

            if (list.length > 0) {
                list.forEach((element) => {
                    if (element.xueyuan == club_name) {
                        club_list.push(element);
                    }
                });
            }

            console.log();
            levels[2].departments_list2 = club_list;
            this.setData({
                acadenmic: club_name,
                levels: levels
            });
        },

        toCreate_Club: function () {
            uni.navigateTo({
                url: '/pages/association/association'
            });
        }
    }
};
</script>
<style>
@import './index.css';
</style>
