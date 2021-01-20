export interface IWeek {
  id?: number;
  label?: string;
  monthNum?: number;
  year?: number;
  weekNum?: number;
  isActive?: boolean;
}

export class Week implements IWeek {
  constructor(
    public id?: number,
    public label?: string,
    public monthNum?: number,
    public year?: number,
    public weekNum?: number,
    public isActive?: boolean
  ) {
    this.isActive = this.isActive || false;
  }
}
