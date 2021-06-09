# TIcTacToeWithAI
A terminal tic-tac-toe game that can be played between 2 human players, human player and an AI, or between 2 AIs.

# Usage
When starting the game, user can choose between commands:
<pre>
start &lt;player&gt; &lt;player&gt;
exit
</pre>
<br>
<b>&lt;player&gt;</b> can be any of the following:<br>
<dl>
  <dt>user</dt>
  <dd>A human player.</dd>
  <dt>easy</dt>
  <dd>Easy AI, which will make random moves.</dd>
  <dt>medium</dt>
  <dd>Medium AI. Medium AI will check if it can win it the current turn. If so, it will make the necessary move to win. If not, it will check if the enemy can win in their next turn. If so, AI will block that move. Otherwise it will make a random move.</dd>
  <dt>hard</dt>
  <dd>Hard AI uses <a href="https://en.wikipedia.org/wiki/Minimax" title="Minimax algorithm">Minimax algorithm</a> to ensure it can never lose - it will play to win or force a draw.</dd>
</dl>
<br>
Human players during their turn  must specify coordinates in format of horizontalPosition verticalPosition from range &lt;1; 3&gt;. The program will only accept these digits. Using wrong amount of parameters or wrong ones, will make the program ask the user to repeat coordinates in the correct format.
