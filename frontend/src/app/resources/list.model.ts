export class List<T> {
  private items: Array<T>;
  private index: number;

  constructor() {
    this.items = [];
    this.index = -1;
  }

  setIndex(value: number) {
    this.index = value;
  }

  getIndex(): number {
    return this.index;
  }

  size(): number {
    return this.items.length;
  }

  addAll(array: Array<T>) {
    this.items = array;
  }

  add(value: T): void {
    this.items.push(value);
  }

  previous() {
    if (this.index > 0)
      return this.items[--this.index];
    else return this.items[this.index];
  }

  get(): T {
    return this.items[this.index];
  }

  next(): T {
    if (this.index < this.size() - 1)
      return this.items[++this.index];
    else return this.items[this.index];
  }

  getByIndex(index: number): T {
    return this.items[index];
  }

}
