export class List<T> {
  private items: Array<T>;
  private index: number;

  constructor() {
    this.items = [];
    this.index = 0;
  }

  setIndex(value: number) {
    this.index = value;
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

  get(): T {
    if (this.index < (this.size() - 1)) {
      return this.items[++this.index];
    }
    return this.items[this.index];
  }

  getByIndex(index: number): T {
    return this.items[index];
  }

  getPrevious(): T {
    if (this.index > 0) {
      return this.items[--this.index];
    }
    return this.items[this.index];
  }
}
