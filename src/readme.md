### Overview


# Design


## Audio Persistence
first thoughts are to write a record for each phrase/audio/timestamp pair
so there is an exact replica of everything that happened

An individual record of audio data can be sent to the speaker on demand

Further records can sew the pieces together, ie by associating them with some entity
like a note or a question

Later on sewn together audio data can be saved to a wav file if needed

## material design icons
go there for the list of material design icons to use in fxml

use the lower case id's

[icons](https://kordamp.org/ikonli/cheat-sheet-materialdesign2.html#_a_materialdesigna)

example
````
    <Button onAction="#onHelloButtonClick">
        <graphic>
            <FontIcon iconLiteral="mdi2a-alarm-check" iconSize="64"/>
        </graphic>
    </Button>
````