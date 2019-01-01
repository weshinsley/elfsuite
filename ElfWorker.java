
public class ElfWorker {
  
  void exec(int[] reg, int X, int A, int B, int C) {
    switch(X) {
      case 0: reg[C] = reg[A] + reg[B];               break; // ADDR
      case 1: reg[C] = reg[A] + B;                    break; // ADDI
      case 2: reg[C] = reg[A] * reg[B];               break; // MULR
      case 3: reg[C] = reg[A] * B;                    break; // MULI
      case 4: reg[C] = reg[A] & reg[B];               break; // BANR
      case 5: reg[C] = reg[A] & B;                    break; // BANI
      case 6: reg[C] = reg[A] | reg[B];               break; // BORR
      case 7: reg[C] = reg[A] | B;                    break; // BORI
      case 8: reg[C] = reg[A];                        break; // SETR
      case 9: reg[C] = A;                             break; // SETI
      case 10: reg[C] = (A > reg[B])? 1 : 0;          break; // GTIR
      case 11: reg[C] = (reg[A] > B)? 1 : 0;          break; // GTRI
      case 12: reg[C] = (reg[A] > reg[B])? 1 : 0;     break; // GTRR
      case 13: reg[C] = (A == reg[B])? 1 : 0;         break; // EQIR
      case 14: reg[C] = (reg[A] == B)? 1 : 0;         break; // EQRI
      case 15: reg[C] = (reg[A] == reg[B]) ? 1 : 0;          // EQRR
    }
  }
}
