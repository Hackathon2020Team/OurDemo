<!--
<template>
    <div>
        <div class="menu-container">
            <el-col >
                <el-menu default-active="2" class="el-menu-vertical-demo" >
                    <el-submenu index="1">
                        <template slot="title">
                            <span :span="4">导航1</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="1-1">选项1</el-menu-item>
                            <el-menu-item index="1-2">选项2</el-menu-item>
                            <el-menu-item v-for="i in task_data" :key="task_data.key">{{task_data.out}}</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="2">
                        <template slot="title">
                            <span>导航2</span>
                        </template>
                    </el-submenu>
                </el-menu>
            </el-col>

        </div>
        <div  id="myChart" :style="{width: '85%', height: '400%'}"></div>
    </div>

</template>

<script>
    //var myChart = this.$echarts.init(document.getElementById('myChart'))
    export default {
        name: "graph",
        data () {
            return {
                msg: 'Welcome to Your Vue.js App',
                task_data:[
                    {key:1,out:'1'},{key:2,out:'1'},{key:3,out:'1'},
                ],
                change_data:[
                    {name: 'qqq', x: 550,y: 100,value:2},
                    {name: 'me',x: 550,y: 300,value: 4},
                    {name: 'ooo',x: 350,y: 300,value: 8},
                ],
                changeout_data:[
                    {name: '11', x: 0, y: 500,},
                    {name: '12', x: 10, y: 800,},
                    {name: '13', x: 20, y: 700,},
                    {name: '15', x: 30, y: 600,},
                ],
                changeout_links:[
                    {
                        source: '11',
                        target: '12'
                    },{
                        source: '12',
                        target: '13'
                    },{
                        source: '13',
                        target: '14'
                    }
                ],
                base_data:[],
                base_links:[],
                dataGet:{
                    "code": 0,
                    "data": [
                        {
                            "taskId": 1,
                            "superTaskId": -1,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫卫生",
                            "details": "认真打扫宿舍卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "EXECUTING",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": []
                        },
                        {
                            "taskId": 2,
                            "superTaskId": 1,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": []
                        },
                        {
                            "taskId": 3,
                            "superTaskId": 2,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": []
                        }
                        ,
                        {
                            "taskId": 4,
                            "superTaskId": 3,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 5,
                            "superTaskId": 4,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 6,
                            "superTaskId": 5,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 7,
                            "superTaskId": 6,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 8,
                            "superTaskId":6,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 9,
                            "superTaskId": 6,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 10,
                            "superTaskId": 6,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 11,
                            "superTaskId": 7,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 12,
                            "superTaskId": 3,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 13,
                            "superTaskId": 4,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 14,
                            "superTaskId": 5,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 15,
                            "superTaskId": 12,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 16,
                            "superTaskId": 1,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 17,
                            "superTaskId": 16,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                        ,
                        {
                            "taskId": 18,
                            "superTaskId": 15,
                            "organizationId": 1,
                            "createUserId": 1,
                            "name": "打扫天空卫生",
                            "details": "认真打扫天空卫生",
                            "createTime": "2020-11-27T23:33:47",
                            "deadLine": "2020-11-27T23:33:24",
                            "endTime": null,
                            "status": "RELEASE",
                            "realName": "王万峰",
                            "content": "舍长",
                            "url": "https://twitter-content.oss-cn-shanghai.aliyuncs.com/1.jpg",
                            "departmentName": "舍长大人",
                            "departmentLevel": 1,
                            "doTasks": [],
                            "administerTasks": [{
                                "administerTaskId": 2,
                                "taskId": 2,
                                "userId": 2,
                                "details": "认真打扫宿舍天空卫生",
                                "receiveMsg": null,
                                "checkMsg": null,
                                "createTime": "2020-11-27T23:33:47",
                                "receiveTime": null,
                                "checkTime": null,
                                "finishTime": "2020-11-27T23:33:24",
                                "personTaskStatus": "NOT_RECEIVE"
                            }]
                        }
                    ]
                },
            }
        },
        mounted(){
            this.drawLine();
            this.analysis();
        },

        methods: {
            analysis:function(){


            },

            drawLine(){
                // 基于准备好的dom，初始化echarts实例
                let myChart = this.$echarts.init(document.getElementById('myChart'))
                var the_data=this.dataGet.data

                myChart.on('click', function (params) {
                    var temp = myChart.getOption()
                    var datain = []
                    var linkin = []
                    for (var i=0; i<the_data.length;i++){
                        if(the_data[i].taskId==params.value){
                            var pushin={name: ' ', x: 0,y: 0,value:0,dataIndex:1}
                            pushin.value=the_data[i].taskId
                            pushin.name='now'+the_data[i].taskId
                            pushin.x=550
                            pushin.y=300
                            datain.push(pushin)
                            for (var j=0; j<the_data.length;j++){
                                if(the_data[i].superTaskId==the_data[j].taskId){
                                    var pushfin={name: ' ', x: 0,y: 0,value:0,dataIndex:2}
                                    var linksin = {source: '',target: ''}
                                    pushfin.value=the_data[j].taskId
                                    pushfin.name='father'+the_data[j].taskId
                                    pushfin.x=550
                                    pushfin.y=100
                                    linksin.source=pushin.name
                                    linksin.target=pushfin.name
                                    datain.push(pushfin)
                                    linkin.push({source: pushfin.name,target: pushin.name})
                                    console.log('the_data')
                                    console.log(the_data[j].taskId)
                                }
                                if(the_data[j].superTaskId==params.value){
                                    var pushsin={name: ' ', x: 0,y: 0,value:0,dataIndex:3}
                                    pushsin.value=the_data[j].taskId
                                    pushsin.name='son'+(j+1)
                                    pushsin.x=500+j*30
                                    pushsin.y=500
                                    pushsin.dataIndex=j+12
                                    linkin.push({source: pushin.name,target: pushsin.name})
                                    datain.push(pushsin)

                                }
                            }
                            break;
                        }
                    }
                    //console.log('params')
                    //console.log(params)
                    //console.log(temp.series[0].links)
                    temp.series[0].data=datain
                    temp.series[0].links=linkin
                    console.log(temp.graphic[0].elements[2])
                    temp.graphic[0].elements[2].style.text ='QQQQQQQQQ'
                    myChart.setOption(temp);
                    var tout = myChart.getOption()

                    //console.log("after data")
                    //console.log(tout.series[0])
                });

                myChart.setOption({
                    title: {
                        text: 'Graph 简单示例'
                    },
                    tooltip: {},
                    animationDurationUpdate: 1500,
                    animationEasingUpdate: 'quinticInOut',

                    series: [
                        {
                            type: 'graph',
                            layout: 'none',
                            symbolSize: 50,
                            roam: true,
                            label: {
                                show: true
                            },
                            edgeSymbol: ['circle','arrow'],
                            edgeSymbolSize: [4, 10],
                            edgeLabel: {
                                fontSize: 20
                            },
                            data: this.change_data,
                            links: [
                                {
                                source: 'qqq',
                                target: 'me'
                                },{
                                source: 'me',
                                target: 'ooo'
                                },{
                                source: 'qqq',
                                target: 'ooo'
                            }
                            ],
                            lineStyle: {
                                opacity: 0.9,
                                width: 2,
                                curveness: 0
                            }
                        },
                    ],
                    graphic: [
                        {
                            type: 'group',
                            left: '60%',
                            top: 'center',
                            children: [
                                {
                                    type: 'rect',
                                    z: 100,
                                    x:700,
                                    left: 'center',
                                    top: 'middle',
                                    shape: {
                                        width: 190,
                                        height: 90
                                    },
                                    style: {
                                        fill: '#fff',
                                        stroke: '#555',
                                        lineWidth: 2,
                                        shadowBlur: 8,
                                        shadowOffsetX: 3,
                                        shadowOffsetY: 3,
                                        shadowColor: 'rgba(0,0,0,0.3)'
                                    }
                                },
                                {
                                    type: 'text',
                                    z: 100,
                                    left: 'center',
                                    top: 'middle',
                                    style: {
                                        fill: '#333',
                                        text: [
                                            '横轴表示温度，单位是°C',
                                            '纵轴表示高度，单位是km',
                                            '右上角有一个图片做的水印',
                                            '这个文本块可以放在图中各',
                                            '种位置'
                                        ].join('\n'),
                                        font: '14px Microsoft YaHei'
                                    }
                                }
                            ]
                        }
                    ],
                });
            }
        },
    }
</script>

<style lang="scss" scoped>
.menu-container{
    position: relative;
    float: right;
}
</style>
-->
