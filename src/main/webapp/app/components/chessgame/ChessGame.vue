<script>
import {chessboard} from 'vue-chessboard'

export default {
    extends: chessboard,
    props: {
        lastMove: {
            type: String,
            default: '',
        }
    },
    watch: {
        lastMove: function (newLastMove) {
            this.lastMove = newLastMove
            this.move()
        }
    },
    methods: {
        // Copy this method from the superclass
        possibleMoves () {
            const dests = {}
            this.game.SQUARES.forEach(s => {
                const ms = this.game.moves({square: s, verbose: true})
                if (ms.length) dests[s] = ms.map(m => m.to)
            })
            return dests
        },
        // Copy this method from the superclass
        toColor () {
            return (this.game.turn() === 'w') ? 'white' : 'black'
        },
        move () {
            this.game.move(this.lastMove)
            this.board.set({
                fen: this.game.fen(),
                turnColor: this.toColor(),
                movable: {
                    color: this.toColor(),
                    dests: this.possibleMoves(),
                },
                orientation: this.orientation,
            })
        },
    }
}
</script>

