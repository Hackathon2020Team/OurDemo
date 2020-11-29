<template>
    <div class="graph-container">


        <div id="myChart" :style="{width: '80%', height: '400%'}"></div>
        <div class="button-container">
            <el-button type="primary" @click="create_graph">create a graph</el-button>
        </div>
    </div>

</template>

<script>
    const axios = require('axios');
    export default {
        name: "graph",
        data () {
            return {
                msg: 'Welcome to Your Vue.js App',
                task_data:[
                    {key:1,out:'1'},{key:2,out:'1'},{key:3,out:'1'},
                ],
                change_data:[
                    {name: 'father', x: 550,y: 100,value:1},
                    {name: 'me',x: 550,y: 300,value: 2},
                ],
                myChart: {},
                count: 0,
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
                user_data: {},
                tasks: [
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
                ],
            }
        },
        mounted(){
            this.myChart=this.$echarts.init(document.getElementById('myChart'))
            this.user_data=this.$cookies.get('user_data');
            axios.get('http://njuzhy.com:7800/task/my',
                {params:{token:this.user_data.token}}).then(/*(res)=>this.tasks=res.data.data*/).then((res)=>console.log('oooooooooCCCCCCC',this.tasks)).then(/*(res)=>this.initial_graph()*/);
             this.initial_graph();
            console.log('CCCCCCCCCCCC')
        },
        methods:{
            initial_graph:function(){
                let optionin = {
                    title: {
                        text: 'Graph 简单示例'
                    },
                    nouse:'QQQQQQQQQQ',
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
                            data: [],
                            links: [],
                            lineStyle: {
                                opacity: 0.9,
                                width: 2,
                                curveness: 0
                            }
                        }
                    ]
                }
                console.log('QAQQQQQQQQQQQQQQQQ')
                console.log(this.tasks)
                for (let i=0; i<this.tasks.length;i++){
                    if(this.tasks[i].taskId==this.tasks[0].taskId){
                        let pushin={name: ' ', x: 0,y: 0,value:0,dataIndex:1}
                        pushin.value=this.tasks[i].taskId
                        pushin.name='now'+this.tasks[i].taskId
                        pushin.x=550
                        pushin.y=300
                        optionin.series[0].data.push(pushin)
                        for (let j=0; j<this.tasks.length;j++){
                            if(this.tasks[i].superTaskId==this.tasks[j].taskId){
                                let pushfin={name: ' ', x: 0,y: 0,value:0,dataIndex:2}
                                let linksin = {source: '',target: ''}
                                pushfin.value=this.tasks[j].taskId
                                pushfin.name='father'+this.tasks[j].taskId
                                pushfin.x=550
                                pushfin.y=100
                                linksin.source=pushin.name
                                linksin.target=pushfin.name
                                optionin.series[0].data.push(pushfin)
                                optionin.series[0].links.push({source: pushfin.name,target: pushin.name})
                                console.log('the_data')
                                console.log(this.tasks[j].taskId)
                            }
                            if(this.tasks[j].superTaskId==this.tasks[i]){
                                let pushsin={name: ' ', x: 0,y: 0,value:0,dataIndex:3}
                                pushsin.value=this.tasks[j].taskId
                                pushsin.name='son'+(j+1)
                                pushsin.x=500+j*30
                                pushsin.y=500
                                pushsin.dataIndex=j+12
                                optionin.series[0].links.push({source: pushin.name,target: pushsin.name})
                                optionin.series[0].data.push(pushsin)

                            }
                        }
                        break;
                    }
                }
                console.log('XXXXXXXXXXXXXXXXXXXQQQQ')
                console.log(optionin.series[0])
                this.myChart.setOption(optionin);
                let the_data=this.tasks;
                let temp = this.myChart.getOption();
                this.myChart.on('click', function (params) {
                    let datain = []
                    let linkin = []
                    for (let i=0; i<the_data.length;i++){
                        if(the_data[i].taskId==params.value){
                            let pushin={name: ' ', x: 0,y: 0,value:0,dataIndex:1}
                            pushin.value=the_data[i].taskId
                            pushin.name='now'+the_data[i].taskId
                            pushin.x=550
                            pushin.y=300
                            datain.push(pushin)
                            for (let j=0; j<the_data.length;j++){
                                if(the_data[i].superTaskId==the_data[j].taskId){
                                    let pushfin={name: ' ', x: 0,y: 0,value:0,dataIndex:2}
                                    let linksin = {source: '',target: ''}
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
                                    let pushsin={name: ' ', x: 0,y: 0,value:0,dataIndex:3}
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
                    temp.series[0].data=datain
                    temp.series[0].links=linkin
                    console.log('this')
                    console.log(this.getOption())
                    console.log('this')
                    this.setOption(temp)
                });

            },

            create_graph:function () {
                let temp = this.myChart.getOption();
                let length = temp.series[0].data.length - 2;
                let temp_data = {}, temp_link = {};
                let taskaddin = {}
                temp_data.name = "su" + length;
                console.log(temp_data.name)
                for(let i=2;i<length+2;i++){
                    temp.series[0].data[i].x = (1100 / (length + 2)) * (i-1);
                }
                temp_data.x = (1100 / (length + 2)) * (length + 1);
                temp_data.y = 600;
                temp_data.value = 1+length;
                temp_link.source = temp.series[0].data[0].name;
                temp_link.target = temp_data.name;

                temp.series[0].data.push(temp_data);
                temp.series[0].links.push(temp_link);

                this.myChart.setOption(temp);

            }
        }

    }
</script>

<style lang="scss" scoped>
    .graph-container{
        min-height: 100%;
        width: 100%;
        background-color: white;
    }
    .menu-container{
        position: relative;
        float: right;
    }
</style>
