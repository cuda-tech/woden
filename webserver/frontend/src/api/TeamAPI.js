import axios from 'axios';

export default {
    /**
     * 删除项目组
     * @param id: 项目组 ID
     * @param callback(): 回调函数
     */
    delete(id, callback) {
        axios.delete(`/team/${id}`).then(callback());
    },

    /**
     * 创建项目组
     * @param name: 项目组名称
     * @param callback(team): 回调函数，返回创建的项目组
     */
    create({name}, callback) {
        let params = new FormData();
        params.set('name', name);
        axios.post('/team', params).then(data => callback(data.team));
    },

    /**
     * 更新项目组
     * @param id: 项目组 ID
     * @param name: 项目组名称
     * @param callback(team): 回调函数，返回更新后的项目组
     */
    update(id, {name = null}, callback) {
        let params = new FormData();
        params.set('name', name);
        axios.put(`/team/${id}`, params).then(data => callback(data.team));
    },

    /**
     * 通过 ID 查找项目组
     * @param id: 项目组 ID
     * @param callback(team): 回调函数，返回指定 ID 的项目组
     */
    find(id, callback) {
        axios.get(`/team/${id}`).then(data => callback(data.team))
    },

    /**
     * 分页查询
     * @param pageId: 页面 ID, 从 1 开始计数
     * @param pageSize: 页面大小
     * @param like: 模糊查询词
     * @param callback(count, teams): 回调函数，返回符合要求的项目组总数和分页项目组列表
     */
    listing({pageId, pageSize, like = null}, callback) {
        let params = {
            page: pageId,
            pageSize: pageSize
        };
        if (like !== null && like.trim() !== '') {
            params['like'] = like;
        }
        axios.get('/team', {params: params}).then(data => callback(data.count, data.teams))
    }
}
