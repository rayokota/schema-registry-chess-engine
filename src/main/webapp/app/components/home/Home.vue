<template>
    <div class="home row">
        <div class="col-md-3">
            <span class="hipster img-fluid rounded"></span>
        </div>
        <div class="col-md-9">
            <p class="lead">Welcome to the Confluent Schema Registry Browser!</p>

            <p>
                For more information on the Confluent Schema Registry:
            </p>

            <ul>
                <li><a href="https://docs.confluent.io/current/schema-registry/docs/index.html" target="_blank" rel="noopener">Schema Registry documentation</a></li>
                <li><a href="https://github.com/confluentinc/schema-registry" target="_blank" rel="noopener">Schema Registry source code</a></li>
                <li><a href="https://github.com/confluentinc/schema-registry/issues" target="_blank" rel="noopener">Schema Registry issue tracker</a></li>
            </ul>

            <p>
                <span>If you like the Confluent Schema Registry Browser, don't forget to give us a star on</span> <a
                href="https://github.com/rayokota/schema-registry-browser" target="_blank" rel="noopener">GitHub</a>!
            </p>
        </div>

        <h1>Chessboard with binded onmove method. Shows threats on text area</h1>
        <chessgame :lastMove="currentLastMove" @onMove="showInfo"/>
        <div>
            Color: {{this.positionInfo}}
        </div>
    </div>
</template>

<script>
    import Chess from 'chess.js'
    import ChessGame from '../chessgame/ChessGame'
    import 'vue-chessboard/dist/vue-chessboard.css'
    import axios from 'axios'

    export default {
        name: 'app',
        components: {
            chessgame: ChessGame
        },
        data () {
            return {
                currentFen: '',
                currentLastMove: '',
                positionInfo: null
            }
        },
        methods: {
            showInfo(data) {
                //this.positionInfo = chess.toColor()
                this.positionInfo = data;
                axios.post(`subjects/test7/versions`, {schemaType: "CHESS", schema: data.history[data.history.length-1]})
                    .then(response => {
                        var id = response.data.id;
                        axios.get(`schemas/ids/${id}`)
                            .then(response => {
                                var pgn = response.data.schema;
                                var chess = new Chess();
                                chess.load_pgn(pgn);
                                var history = chess.history();
                                this.currentLastMove = history[history.length-1];
                                console.log("last " + this.currentLastMove);
                            })
                    })
                    .catch(error => {
                        // See https://github.com/axios/axios/issues/960
                        let errorObject = JSON.parse(JSON.stringify(error));
                        this.error = errorObject.response.data.message;
                    });

            },
            loadFen(fen) {
                this.currentFen = fen
            },
            loadLastMove(lastMove) {
                this.currentLastMove = lastMove
            },
            promote() {
                if (confirm("Want to promote to rook? Queen by default") ) {
                    return 'r'
                } else {
                    return 'q'
                }
            }
        },
        created() {
            this.fens = ['5rr1/3nqpk1/p3p2p/Pp1pP1pP/2pP1PN1/2P1Q3/2P3P1/R4RK1 b - f3 0 28',
                'r4rk1/pp1b3p/6p1/8/3NpP2/1P4P1/P2K3P/R6R w - - 0 22'
            ]
            this.lastMoves = ['d4','e4']
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    /* ==========================================================================
Main page styles
========================================================================== */

    .hipster {
        display: inline-block;
        width: 347px;
        height: 497px;
    }

</style>
