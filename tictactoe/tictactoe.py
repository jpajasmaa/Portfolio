


"""
board 3x3 matrix

2 pelaajaa: x ja o

kone voi randomilla valita ruudun

pelaaja itse

pöytä tarkistaa pelitilanteen ja onko sallittu muuvi jos tekisi luokkana

pelin loppu jos ei kumpikaan enää voi voittaa == tasapeli tai toinen voittaa
"""

"""
# tictactoe skeleton


board = new_board(3x3 matrix)

#players x and o 

#x starts

#loop through turns

ask_for_move()

move()

render(board)

is_valid_move()

iswinner()

boardfull()

"""

BOARD_WIDTH = 3
BOARD_HEIGHT = 3


#    TODO:  siivoa nätimmäksi getmove ja siisti
def get_move(board):
    print("tee liike. muoto: '0 (sarake) 1 (rivi)' jne ")

    while True:
        liike = input("liike: ")
        coords = liike.split()
        nattina = siisti(coords)
        tuplena = tuple(nattina)
        if is_valid_move(board, tuplena):
            break
        else:
            print("Laiton siirto, yritä uudestaan")
    return tuplena


def make_move(board, move, player):
    poyta = board
    poyta[move[0]][move[1]] = player
    return poyta


def is_valid_move(board, liike):
    return board[liike[0]][liike[1]] == None


def check_draw(board):
    for col in board:
        for sq in col:
            if sq is None:
                return False
    return True


# kaikista pöydän riveistä ja sarakkeista ja diagonaaleista listat, jotka tsekataan ja lasketaan läpi monta X ja 0 löytyy
# jos löytyy jotain 3 niin palautetaan voittaja muuten jatketaan läpi, lopulta palautetaan None jos ei kukaan voittanut
def get_winner(board):
    all_line_co_ords = get_all_line_co_ords()
    #print(all_line_co_ords)

    for line in all_line_co_ords:
        line_values = [board[x][y] for (x, y) in line]
        if len(set(line_values)) == 1 and line_values[0] is not None:
            return line_values[0]

    return None


"""
kaikkien rivien, sarakkeiden ja diagonaalien koordinaatit saadaan näin. lopuksi laitetaan yhteiseen listaan joka palautetaan 
"""
def get_all_line_co_ords():
    cols = []
    for x in range(0, BOARD_WIDTH):
        col = []
        for y in range(0, BOARD_HEIGHT):
            col.append((x, y))
        cols.append(col)

    rows = []
    for y in range(0, BOARD_HEIGHT):
        row = []
        for x in range(0, BOARD_WIDTH):
            row.append((x, y))
        rows.append(row)

    diagonals = [
        [(0, 0), (1, 1), (2, 2)],
        [(0, 2), (1, 1), (2, 0)]
    ]
    return cols + rows + diagonals

    
# ['1', '2'] ulos [1,2]
def siisti(lista):
    new_lista = []
    for x in lista:
        y = int(x)
        new_lista.append(y)
    return new_lista


def new_board(width, height):
    return [x[:] for x in [[None]*width]*height]


def render(board):
    print("TicTacToe")
    rivit = []
    for y in range(0, BOARD_HEIGHT):
        rivi = []
        for x in range(0, BOARD_WIDTH):
            rivi.append(board[x][y])
        rivit.append(rivi)

    rivi_num = 0
    print('  0 1 2')
    print('  ------')
    for rivi in rivit:
        outti = ''
        for sq in rivi:
            if sq is None:
                outti += ' '
            else:
                outti += sq
        print("%d|%s|" % (rivi_num, ' '.join(outti)))
        rivi_num += 1
    print('  ------')



class Bot():
    def __init__():
        pass



def main():
    board = new_board(BOARD_WIDTH, BOARD_HEIGHT)
    render(board)
    
    peli = True
    while peli:
        move_coords1 = get_move(board)
        new_board_state = make_move(board, move_coords1, 'X')
        render(board)

        voittaja = get_winner(board)
        if voittaja is not None:
            print(voittaja + " voitti!")
            peli = False
            break

        if check_draw(board) is True:
            peli = False
            
        move_coords2 = get_move(board)
        new_board_state = make_move(board, move_coords2, 'O')
        render(board)

        voittaja = get_winner(board)
        if voittaja is not None:
            print(voittaja + " voitti!")    
            peli = False

        


if __name__ == "__main__":
    main()
