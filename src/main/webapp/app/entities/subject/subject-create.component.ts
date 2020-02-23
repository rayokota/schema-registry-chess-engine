import {integer, maxLength, minLength, pattern, required} from 'vuelidate/lib/validators';

import SubjectService from './subject.service.vue';

const SubjectCreate = {
    mixins: [SubjectService],
    data() {
        return {
            schema: {
                subject: null,
                schema: '{play as white}'
            },
            isSaving: false,
            error: null
        };
    },
    methods: {
        save() {
            this.isSaving = true;
            this.createSubject(this.schema)
                .then(response => {
                    this.$store.commit('setSchemaId', response.data.id);
                    this.$store.commit('setSchema', this.schema.schema);
                    this.$router.push({ name: 'SubjectView', params: { subjectId: this.schema.subject } })
                    this.isSaving = false;
                })
                .catch(error => {
                    // See https://github.com/axios/axios/issues/960
                    let errorObject = JSON.parse(JSON.stringify(error));
                    this.error = errorObject.response.data.message;
                    this.isSaving = false;
                });
        },
        previousState() {
            this.$router.go(-1);
        }
    }
};

export default SubjectCreate;
