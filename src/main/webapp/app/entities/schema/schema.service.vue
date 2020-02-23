<script>
    import axios from 'axios';

    const baseApiUrl = 'schemas';

    const SchemaService = {
        methods: {
            findSchema(id) {
                return axios.get(`${baseApiUrl}/ids/${id}`);
            }
        }
    };

    export default SchemaService;

</script>
