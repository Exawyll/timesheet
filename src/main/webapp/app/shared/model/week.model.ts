import { IActivity } from 'app/shared/model/activity.model';

export interface IWeek {
  id?: number;
  label?: string;
  monthNum?: number;
  year?: number;
  weekNum?: number;
  isActive?: boolean;
  activities?: IActivity[];
}

export class Week implements IWeek {
  constructor(
    public id?: number,
    public label?: string,
    public monthNum?: number,
    public year?: number,
    public weekNum?: number,
    public isActive?: boolean,
    public activities?: IActivity[]
  ) {
    this.isActive = this.isActive || false;
  }
}
