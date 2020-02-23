<script>
    import axios from 'axios';

    const baseApiUrl = 'subjects';

    const SubjectService = {
        methods: {
            createSubject(schema) {
                return axios.post(`subjects/${schema.subject}/versions`, {schema: schema.schema, schemaType: "CHESS"});
            },
            findSubject(id) {
                return axios.get(`${baseApiUrl}/${id}/versions`);
            },
            findConfig(id) {
                return axios.get(`config/${id}`);
            },
            retrieveSubjects() {
                return axios.get(baseApiUrl);
            },
            deleteSubject(id) {
                return axios.delete(`${baseApiUrl}/${id}`);
            },
            deleteVersion(subject, id) {
                return axios.delete(`${baseApiUrl}/${subject}/versions/${id}`);
            },
            updateSubject(subject) {
                return axios.put(`config/${subject.name}`, {compatibility: subject.compatibility});
            },
            findVersion(subject, id) {
                return axios.get(`${baseApiUrl}/${subject}/versions/${id}`);
            }
        }
    };

    export default SubjectService;

</script>
