import Vue from 'vue';
import Router from 'vue-router';

const Home = () => import('../components/home/Home.vue');
const Subject = () => import('../entities/subject/subject.vue');
const SubjectCreate = () => import('../entities/subject/subject-create.vue');
const SubjectUpdate = () => import('../entities/subject/subject-update.vue');
const SubjectDetails = () => import('../entities/subject/subject-details.vue');
const Schema = () => import('../entities/schema/schema.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home
        },
        {path: '/entity/subject', name: 'Subject', component: Subject},
        {path: '/entity/subject/new', name: 'SubjectCreate', component: SubjectCreate},
        {path: '/entity/subject/:subjectId/edit', name: 'SubjectEdit', component: SubjectUpdate},
        {path: '/entity/subject/:subjectId/view', name: 'SubjectView', component: SubjectDetails}, // prettier-ignore
        {path: '/entity/schema', name: 'Schema', component: Schema},
        // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
    ]
});
