import java.util.ArrayList;  

public class MoveChooser {
	
    public static Move chooseMove(BoardState boardState){

	int searchDepth= Othello.searchDepth;

        ArrayList<Move> moves= boardState.getLegalMoves();
        if(moves.isEmpty()){
            return null;
	   }
        // alpha = - infinity beta = + infinity 
        MiniMax(boardState, moves, searchDepth, -10000, 10000);
        return bestMove;
    }
    
    /*
    used the bellow array for the component of static evaluation

    120 -20 20 5 5 20 -20 120
    -20 -40 -5 -5 -5 -5 -40 -20
    20 -5 15 3 3 15 -5 20
    5 -5 3 3 3 3 -5 5
    5 -5 3 3 3 3 -5 5
    20 -5 15 3 3 15 -5 20
    -20 -40 -5 -5 -5 -5 -40 -20
    120 -20 20 5 5 20 -20 120
    */ 
    
    //Array of values for evaluation function
    public static int [][] sValue =
        {{120, -20, 20, 5, 5, 20, -20, 120}
        ,{-20, -40, -5, -5, -5, -5, -40, -20}
        ,{20, -5, 15, 3, 3, 15, -5, 20}
        ,{5, -5, 3, 3, 3, 3, -5, 5}
        ,{5, -5, 3, 3, 3, 3, -5, 5}
        ,{20, -5, 15, 3, 3, 15, -5, 20}
        ,{-20, -40, -5, -5, -5, -5, -40, -20}
        ,{120, -20, 20, 5, 5, 20, -20, 120}};
    
    //Static Evaluation function of a board
    public static int EvaluationFunction(BoardState bState) {
    	int boardValue=0;
    	for (int i=0;i<8;i++) {
    		for (int j=0;j<8;j++) {
    			//(sum of white) -(sum of black) of sValue
    			boardValue = boardValue + (bState.getContents(i, j)*(sValue[i][j]));
    		}
    	}
    	// number of available moves with weight 12
    	if(bState.colour == 1) {
    		boardValue = boardValue + bState.getLegalMoves().size()*12;
    	}
    	else if(bState.colour == -1) {
    		boardValue = boardValue - bState.getLegalMoves().size()*12;
    	}
    	return boardValue;
    }

    //bestMove == best choice of validMove
    public static Move bestMove;

    //minimax function with alpha,beta pruning
    public static int MiniMax(BoardState bState,ArrayList<Move> validMoves ,
    		int sDepth,int alpha, int beta){
    	
    	// when game over or depth is 0 than get eval of the board
        if (sDepth == 0 || bState.gameOver()){
        	
        	//Static Evaluation function
        	return EvaluationFunction(bState);
        }
        
        // when white (Maximizing player) returning Max
        if (bState.colour == 1) {
        	int Maxval = -10000;
        	BoardState TempBoardState = new BoardState();
        	for (int counter=0; counter<validMoves.size();counter++) {
        		//making child
        		TempBoardState = bState.deepCopy();
        		TempBoardState.makeLegalMove(validMoves.get(counter).x,validMoves.get(counter).y);
        		int eval = MiniMax(TempBoardState, TempBoardState.getLegalMoves(), sDepth-1,alpha,beta);
        		
        		// Maxval = max(eval,Maxval)
        		if (eval> Maxval) {
        			Maxval = eval;
        			if(sDepth==Othello.searchDepth) {
        				bestMove = new Move(validMoves.get(counter).x,validMoves.get(counter).y);
        			}
        		}
        		
        		//alpha=max(alpha,eval)
        		if (eval > alpha) {
        			alpha = eval;
        		}
        		
        		//pruning
        		if (beta <= alpha) {
        			break;
        		}
        		
        	}
        	return Maxval;
        }
        
        // when black (minimizing player) returning Min
        else {
        	int Minval = 10000;
        	BoardState TempBoardState = new BoardState();
        	for (int counter=0; counter<validMoves.size();counter++) {
        		//making child
        		TempBoardState = bState.deepCopy();
        		TempBoardState.makeLegalMove(validMoves.get(counter).x,validMoves.get(counter).y);
        		int eval = MiniMax(TempBoardState, TempBoardState.getLegalMoves(), sDepth-1,alpha,beta);
        		
        		//Minval = Min(Minval,eval)
        		if (eval< Minval) {
        			Minval = eval;
        			if(sDepth==Othello.searchDepth) {
        				bestMove = new Move(validMoves.get(counter).x,validMoves.get(counter).y);
        			}
        		}
        		
        		// beta=min(eval,beta)
        		if (eval<beta) {
        			beta =eval;
        		}
        		
        		//pruning
        		if (beta<=alpha) {
        			break;
        		}
        		
        	}
        	return Minval;
        }
     
    // end of minimax code
    }
    
}
