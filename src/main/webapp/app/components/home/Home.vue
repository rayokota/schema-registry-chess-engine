<template>
    <div class="home row">
        <div class="col-md-3">
            <span class="hipster img-fluid rounded"></span>
        </div>
        <div class="col-md-9">
            <p class="lead">Welcome to the Confluent Chess Engine!</p>

            <p>
                This demo shows how custom schemas can be stored and retrieved in Confluent Schema Registry.
            </p>

            <p>
                In this case, the custom schemas take the form of PGN notation, which is used to describe chess games.
            </p>

            <p>
                For more information on the Confluent Schema Registry:
            </p>

            <ul>
                <li><a href="https://docs.confluent.io/current/schema-registry/docs/index.html" target="_blank" rel="noopener">Schema Registry documentation</a></li>
                <li><a href="https://github.com/confluentinc/schema-registry" target="_blank" rel="noopener">Schema Registry source code</a></li>
                <li><a href="https://github.com/confluentinc/schema-registry/issues" target="_blank" rel="noopener">Schema Registry issue tracker</a></li>
            </ul>

            <p>
                <span>If you like the Confluent Chess Engine, don't forget to give us a star on</span> <a
                href="https://github.com/rayokota/schema-registry-chess-engine" target="_blank" rel="noopener">GitHub</a>!
            </p>
        </div>
    </div>
</template>

<script lang="ts" src="./Home.component.ts">
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
